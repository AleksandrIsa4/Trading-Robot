package com.enfernuz.quik.lua.rpc.service;

import com.enfernuz.quik.lua.rpc.api.messages.GetMoney;
import com.enfernuz.quik.lua.rpc.api.messages.GetParamEx;
import com.enfernuz.quik.lua.rpc.api.messages.Message;
import com.enfernuz.quik.lua.rpc.api.messages.SendTransaction;
import com.enfernuz.quik.lua.rpc.api.structures.Money;
import com.enfernuz.quik.lua.rpc.api.zmq.ZmqTcpQluaRpcClient;
import com.enfernuz.quik.lua.rpc.mapper.TransactionRPCMapper;
import com.enfernuz.quik.lua.rpc.producer.ProducerRpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.*;
import org.Isa4.dto.enumeration.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@EnableKafka
@RequiredArgsConstructor
@Service
public class RpcService {


    private final ZmqTcpQluaRpcClient rpcClient;

    private final ProducerRpc producerRpc;

    @Value("${class.code}")
    private String CLASS_CODE;

    public void info() {
        final Integer result = rpcClient.qlua_message("Hello, world!", Message.IconType.WARNING);
        if (result == null) {
            log.error("Удалённая процедура 'message' выполнилась с ошибкой.");
        } else {
            log.info("Результат выполнения удалённой процедуры 'message': {}.", result);
        }
    }

    // Получение необходимой информации по инструменту
    public InformationToolDto infoParamEx(ParamExAll param) {
        return InformationToolDto.builder()
                .classCode(param.getClassCode())
                .secCode(param.getSecCode())
                .bid(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "BID")).getParamValue()))
                .biddepth(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "BIDDEPTH")).getParamValue()))
                .biddeptht(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), " BIDDEPTHT")).getParamValue()))
                .offer(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OFFER")).getParamValue()))
                .offerdepth(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OFFERDEPTH")).getParamValue()))
                .offerdeptht(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OFFERDEPTHT")).getParamValue()))
                .last(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "LAST")).getParamValue()))
                .open(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(param.getClassCode(), param.getSecCode(), "OPEN")).getParamValue()))
                .createdTime(LocalDateTime.now())
                .build();
    }

    // Получение доступных акций на счете
    public List<PositionInstrumentDto> infoPositionInstrument(InformationAccountDto informationAccountDto) {
        List<PositionInstrumentDto> fullPositionInstruments = new ArrayList<>();
        infoMoney(informationAccountDto);
        List<Map<String, String>> fullInstrument = infoAkzii(informationAccountDto.getClientCode(), informationAccountDto.getAccount());
        for (Map<String, String> element : fullInstrument) {
            PositionInstrumentDto positionInstrument = PositionInstrumentDto.builder()
                    .classCode(informationAccountDto.getClassCode())
                    .secCode(element.get("sec_code"))
                    .quantity((long) Float.parseFloat(element.get("currentbal")))
                    .averagePrice(Float.parseFloat(element.get("awg_position_price")))
                    .lotSize((long) Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(informationAccountDto.getClassCode(), element.get("sec_code"), "LOTSIZE")).getParamValue()))
                    .secPriceStep(Float.parseFloat(rpcClient.qlua_getParamEx(new GetParamEx.Args(informationAccountDto.getClassCode(), element.get("sec_code"), "SEC_PRICE_STEP")).getParamValue()))
                    .build();
            fullPositionInstruments.add(positionInstrument);
        }
        return fullPositionInstruments;
    }

    // Получение доступных средств на счете
    private void infoMoney(InformationAccountDto informationAccountDto) {
        Money money = rpcClient.qlua_getMoney(new GetMoney.Args(informationAccountDto.getClientCode(), informationAccountDto.getFirmId(), informationAccountDto.getTagMoney(), "SUR"));
        producerRpc.sendMoneyInfo(MoneyInfo.builder()
                .money(Float.parseFloat(money.getMoneyLimitAvailable()))
                .tagMoney(informationAccountDto.getTagMoney())
                .clientCode(informationAccountDto.getClientCode())
                .firmId(informationAccountDto.getFirmId())
                .build());
    }

    // Получение списка доступных акций на счете (limit_kind равно 0)
    private List<Map<String, String>> infoAkzii(String clientCode, String account) {
        List<Map<String, String>> fullInstrument = new ArrayList<>();
        Map<String, String> instruments;
        int lineDepoLimits = rpcClient.qlua_getNumberOf("depo_limits");
        for (int i = 0; i < lineDepoLimits; i++) {
            instruments = rpcClient.qlua_getItem("depo_limits", i);
            if (instruments.get("client_code").equals(clientCode) && instruments.get("limit_kind").equals("0") && instruments.get("trdaccid").equals(account)) {
                fullInstrument.add(instruments);
            }
        }
        return fullInstrument;
    }

    // Отправка заявки
    public String sendTrade(TransactionDto transactionDto) {
        Map<String, String> transactionMap = TransactionRPCMapper.toMap(transactionDto);
        SendTransaction.Args sendTransaction = new SendTransaction.Args(transactionMap);
        String result = rpcClient.qlua_sendTransaction(sendTransaction);
        log.info("Результат выполнения удалённой процедуры 'result': {}.", result);
        transactionDto = addOrder(transactionDto);
        transactionDto = checkOrder(transactionDto);
        transactionDto = checkTransaction(transactionDto);
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
        return result;
    }

    // Добавление к заявки order_num,balance, orderGetItem
    private TransactionDto addOrder(TransactionDto transactionDto) {
        sleep();
        int lineOrders = rpcClient.qlua_getNumberOf("orders");
        Map<String, String> instruments;
        for (int i = lineOrders - 1; i >= 0; i--) {
            instruments = rpcClient.qlua_getItem("orders", i);
            if (instruments.get("trans_id").equals(transactionDto.getTransId().toString())) {
                transactionDto.setOrderGetItem(i);
                transactionDto.setQuantityComplete((int) Float.parseFloat(instruments.get("balance")));
                transactionDto.setOrderNumber(Long.parseLong(instruments.get("order_num")));
                transactionDto.setStatus(Status.ACTIVE);
                return transactionDto;
            }
        }
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
        return transactionDto;
    }

    // Проверка заявки на выполнение, заполнение quantityComplete (количество выполненных операций)
    private TransactionDto checkOrder(TransactionDto transactionDto) {
        sleep();
        Map<String, String> instruments = rpcClient.qlua_getItem("orders", transactionDto.getOrderGetItem());
        if (instruments.get("balance").equals("0.0")) {
            transactionDto.setStatus(Status.COMPLETED);
            transactionDto.setQuantityComplete(transactionDto.getQuantity());
            return transactionDto;
        }
        int quantityComplete = transactionDto.getQuantity() - (int) Float.parseFloat(instruments.get("balance"));
        transactionDto.setQuantityComplete(quantityComplete);
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
        return transactionDto;
    }

    // TODO: Не доделана
    // Проверка сделки
    private TransactionDto checkTransaction(TransactionDto transactionDto) {
        sleep();
        Map<String, String> instruments;
        int lineTrans = rpcClient.qlua_getNumberOf("trades");
        for (int i = lineTrans - 1; i >= 0; i--) {
            instruments = rpcClient.qlua_getItem("trades", i);
            if (instruments.get("order_num").equals(transactionDto.getOrderNumber().toString())) {
                transactionDto.setTransGetItem(i);
                transactionDto.setTransNumber(Long.parseLong(instruments.get("trade_num")));
                return transactionDto;
            }
        }
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
        return transactionDto;
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
}