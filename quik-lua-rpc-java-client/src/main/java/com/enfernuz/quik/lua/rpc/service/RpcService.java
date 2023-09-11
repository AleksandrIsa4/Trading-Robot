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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@EnableKafka
@RequiredArgsConstructor
@Service
public class RpcService {

    private final ZmqTcpQluaRpcClient rpcClient;

    private final ReplyQUIKservice replyQUIKservice;

    private final ProducerRpc producerRpc;

    private final Set<ParamExAll> paramExAllList;

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

    public void postListParamEx(ParamExAll param) {
        log.info("RpcService postListParamEx");
        paramExAllList.add(param);
    }

    @Scheduled(fixedRate = 3000)
    public void getListParamEx() {
        log.info("RpcService getListParamEx");
        System.out.println("Список акций1  " + paramExAllList);
        if (!paramExAllList.isEmpty()) {
            System.out.println("Список акций2  " + paramExAllList);
            paramExAllList.forEach(paramExAll -> {
                InformationToolDto informationToolDto = infoParamEx(paramExAll);
                producerRpc.sendInformationTool(informationToolDto);
            });
        }
    }

    // Получение необходимой информации по инструменту
    public InformationToolDto infoParamEx(ParamExAll param) {
        log.info("RpcService infoParamEx  param {}", param);
        postListParamEx(param);
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
        log.info("RpcService infoPositionInstrument informationAccountDto {}", informationAccountDto);
        List<PositionInstrumentDto> fullPositionInstruments = new ArrayList<>();
        infoMoney(informationAccountDto);
        List<Map<String, String>> fullInstrument = infoAkzii(informationAccountDto.getClientCode(), informationAccountDto.getAccount());
        log.info("RpcService infoPositionInstrument fullInstrument {}", fullInstrument);
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
        log.info("RpcService infoPositionInstrument fullPositionInstruments {}", fullPositionInstruments);
        return fullPositionInstruments;
    }

    // Получение доступных средств на счете
    private InformationAccountDto infoMoney(InformationAccountDto informationAccountDto) {
        log.info("RpcService infoMoney informationAccountDto {}", informationAccountDto);
        Money money = rpcClient.qlua_getMoney(new GetMoney.Args(informationAccountDto.getClientCode(), informationAccountDto.getFirmId(), informationAccountDto.getTagMoney(), "SUR"));
        informationAccountDto.setMoney(Float.parseFloat(money.getMoneyLimitAvailable()));
        return informationAccountDto;
    }

    // Получение списка доступных акций на счете (limit_kind равно 0)
    private List<Map<String, String>> infoAkzii(String clientCode, String account) {
        log.info("RpcService infoAkzii  clientCode={}, account={}", clientCode, account);
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

    public void parseTrade(List<TransactionDto> dtos) {
        dtos.forEach(this::sendTrade);
    }

    @Async
    // Отправка заявки
    public void sendTrade(TransactionDto transactionDto) {
        log.info("RpcService sendTrade  transactionDto {}", transactionDto);
        Map<String, String> transactionMap = TransactionRPCMapper.toMap(transactionDto);
        SendTransaction.Args sendTransaction = new SendTransaction.Args(transactionMap);
        String result = rpcClient.qlua_sendTransaction(sendTransaction);
        log.info("Результат выполнения удалённой процедуры 'result': {}.", result);
        replyQUIKservice.getTransReply(transactionDto);
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
        checkTransaction(transactionDto);
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
    }

    // Добавление к заявки order_num,balance, orderGetItem
    private TransactionDto addOrder(TransactionDto transactionDto) {
        log.info("RpcService addOrder  transactionDto {}", transactionDto);
        //     sleep();
        log.info("Результат выполнения addOrder1 'transactionDto': {}.", transactionDto);
        int lineOrders = rpcClient.qlua_getNumberOf("orders");
        Map<String, String> instruments;
        for (int i = lineOrders - 1; i >= 0; i--) {
            instruments = rpcClient.qlua_getItem("orders", i);
            log.info("Результат выполнения addOrder 'instruments' trans_id : {}.", instruments.get("trans_id"));
            if (instruments.get("trans_id").equals(transactionDto.getTransId().toString())) {
                log.info("Результат выполнения addOrder 'instruments': {}.", instruments);
                transactionDto.setOrderGetItem(i);
                transactionDto.setQuantityComplete((int) Float.parseFloat(instruments.get("balance")));
                transactionDto.setOrderNumber(Long.parseLong(instruments.get("order_num")));
                transactionDto.setStatus(Status.ACTIVE);
                return transactionDto;
            }
        }
        log.info("Результат выполнения addOrder2 'transactionDto': {}.", transactionDto);
        return transactionDto;
    }

    // Проверка заявки на выполнение, заполнение quantityComplete (количество выполненных операций)
    private TransactionDto checkOrder(TransactionDto transactionDto) {
        log.info("RpcService checkOrder  transactionDto {}", transactionDto);
        //    sleep();
        log.info("Результат выполнения checkOrder1 'transactionDto': {}.", transactionDto);
        Map<String, String> instruments = rpcClient.qlua_getItem("orders", transactionDto.getOrderGetItem());
        if (instruments.get("balance").equals("0.0")) {
            transactionDto.setStatus(Status.COMPLETED);
            transactionDto.setQuantityComplete(transactionDto.getQuantity());
            return transactionDto;
        }
        long quantityComplete = transactionDto.getQuantity() - (long) Float.parseFloat(instruments.get("balance"));
        transactionDto.setQuantityComplete(quantityComplete);
        log.info("Результат выполнения checkOrder2 'transactionDto': {}.", transactionDto);
        return transactionDto;
    }

    // TODO: Не доделана
    // Проверка сделки
    private TransactionDto checkTransaction(TransactionDto transactionDto) {
        //    sleep();
        log.info("Результат выполнения checkTransaction1 'transactionDto': {}.", transactionDto);
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
        log.info("Результат выполнения checkTransaction2 'transactionDto': {}.", transactionDto);
        return transactionDto;
    }
}