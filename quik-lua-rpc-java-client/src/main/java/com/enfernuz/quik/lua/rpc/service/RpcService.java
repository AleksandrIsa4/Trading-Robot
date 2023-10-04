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
import org.Isa4.dto.constant.ModulConstans;
import org.Isa4.dto.enumeration.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
@EnableKafka
@RequiredArgsConstructor
@Service
public class RpcService {

    private final ZmqTcpQluaRpcClient rpcClient;

    private final ReplyQUIKservice replyQUIKservice;

    private final ProducerRpc producerRpc;

    private final Set<ParamExAll> paramExAllList;

    private static final long DELAY_TIME = ModulConstans.DELAY_TIME;

    private static volatile Map<Long, TransactionDto> transactionDtoMap = new ConcurrentHashMap<>();

    // TODO: Не используется
    public void info() {
        final Integer result = rpcClient.qlua_message("Hello, world!", Message.IconType.WARNING);
        if (result == null) {
            log.error("Удалённая процедура 'message' выполнилась с ошибкой.");
        } else {
            log.info("Результат выполнения удалённой процедуры 'message': {}.", result);
        }
    }

    // Добавление позиции инструмента в Set для отслеживания состояния
    public void postListParamEx(ParamExAll param) {
        log.info("RpcService postListParamEx");
        paramExAllList.add(param);
    }

    // Периодическая отправка информации о состоянии отслеживаемых инструментах
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
        log.info("RpcService infoMoney1 informationAccountDto {}", informationAccountDto);
        Money money = rpcClient.qlua_getMoney(new GetMoney.Args(informationAccountDto.getClientCode(), informationAccountDto.getFirmId(), informationAccountDto.getTagMoney(), "SUR"));
        informationAccountDto.setMoney(Float.parseFloat(money.getMoneyLimitAvailable()));
        log.info("RpcService infoMoney2 informationAccountDto {}", informationAccountDto);
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
        log.info("RpcService infoAkzii  fullInstrument={}", fullInstrument);
        return fullInstrument;
    }

    // Отправка на терминал QUIK список транзакций
    public void parseTrade(List<TransactionDto> dtos) {
        dtos.forEach(this::sendTrade);
    }

    // Периодическая проверка информации о состоянии текущих транзакций
    @Scheduled(fixedRate = 5000)
    private void checkOrderStatus() {
        transactionDtoMap.values().forEach(this::checkOrder);
        log.info("RpcService checkOrderStatus  transactionDtoMap={}", transactionDtoMap);
    }

    // Отправка заявки
    @Async
    public void sendTrade(TransactionDto transactionDto) {
        log.info("RpcService sendTrade  transactionDto {}", transactionDto);
        Map<String, String> transactionMap = TransactionRPCMapper.toMap(transactionDto);
        SendTransaction.Args sendTransaction = new SendTransaction.Args(transactionMap);
        String result = rpcClient.qlua_sendTransaction(sendTransaction);
        log.info("RpcService sendTrade Результат выполнения удалённой процедуры 'result': {}.", result);
        if (!result.isEmpty()) {
            System.exit(-1);
        }
        replyQUIKservice.getTransReply(transactionDto);
        transactionDto = runTimeLoop(this::addOrder, transactionDto);
        log.info("Результат выполнения 'transactionDto': {}.", transactionDto);
        producerRpc.sendTransactionLogic(transactionDto);
    }

    // Метод для проверки выполнения запроса на терминал QUIK по ограниченному времени
    public TransactionDto runTimeLoop(Function<TransactionDto, Optional<TransactionDto>> function, TransactionDto transactionDto) {
        LocalDateTime ldt = LocalDateTime.now();
        Optional<TransactionDto> dto;
        try {
            while (LocalDateTime.now().isBefore(ldt.plusSeconds(DELAY_TIME))) {
                log.info("RpcService runTimeLoop");
                dto = function.apply(transactionDto);
                if (dto.isPresent()) {
                    return dto.get();
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.exit(1);
        }
        throw new RuntimeException("Исключение в методе runTimeLoop в классе RpcService из-за задержки получения сообщения");
    }

    // Добавление к заявки order_num,balance, orderGetItem
    private Optional<TransactionDto> addOrder(TransactionDto transactionDto) {
        log.info("RpcService addOrder  transactionDto {}", transactionDto);
        int lineOrders = rpcClient.qlua_getNumberOf("orders");
        Map<String, String> instruments;
        for (int i = lineOrders - 1; i >= 0; i--) {
            instruments = rpcClient.qlua_getItem("orders", i);
            log.info("Результат выполнения addOrder 'instruments' trans_id : {}.", instruments.get("trans_id"));
            if (instruments.get("trans_id").equals(transactionDto.getTransId().toString())) {
                log.info("Результат выполнения addOrder 'instruments': {}.", instruments);
                transactionDto.setOrderGetItem(i);
                transactionDto.setQuantityComplete(transactionDto.getQuantity() - (long) Float.parseFloat(instruments.get("balance")));
                transactionDto.setOrderNumber(Long.parseLong(instruments.get("order_num")));
                if (instruments.get("balance").equals("0.0")) {
                    transactionDto.setStatus(Status.COMPLETED);
                    transactionDto = runTimeLoop(this::checkTransaction, transactionDto);
                } else {
                    transactionDto.setStatus(Status.ACTIVE);
                }
                transactionDtoMap.put(transactionDto.getTransId(), transactionDto);
                return Optional.of(transactionDto);
            }
        }
        log.info("Результат выполнения addOrder2 'transactionDto': {}.", transactionDto);
        return Optional.empty();
        //  return transactionDto;
    }

    // Проверка заявки на выполнение, заполнение quantityComplete (количество выполненных операций)
    private Optional<TransactionDto> checkOrder(TransactionDto transactionDto) {
        log.info("RpcService checkOrder  transactionDto {}", transactionDto);
        Map<String, String> instruments = rpcClient.qlua_getItem("orders", transactionDto.getOrderGetItem());
        if (instruments.get("balance").equals("0.0")) {
            transactionDto.setStatus(Status.COMPLETED);
            transactionDto.setQuantityComplete(transactionDto.getQuantity());
            transactionDto = runTimeLoop(this::checkTransaction, transactionDto);
            return Optional.of(transactionDto);
        }
        long quantityComplete = transactionDto.getQuantity() - (long) Float.parseFloat(instruments.get("balance"));
        transactionDto.setQuantityComplete(quantityComplete);
        log.info("RpcService checkOrder2 'transactionDto': {}.", transactionDto);
        return Optional.of(transactionDto);
    }

    // Проверка сделки
    private Optional<TransactionDto> checkTransaction(TransactionDto transactionDto) {
        //    sleep();
        log.info("Результат выполнения checkTransaction1 'transactionDto': {}.", transactionDto);
        Map<String, String> instruments;
        int lineTrans = rpcClient.qlua_getNumberOf("trades");
        for (int i = lineTrans - 1; i >= 0; i--) {
            instruments = rpcClient.qlua_getItem("trades", i);
            if (instruments.get("order_num").equals(transactionDto.getOrderNumber().toString())) {
                transactionDto.setTransGetItem(i);
                transactionDto.setTransNumber(Long.parseLong(instruments.get("trade_num")));
                producerRpc.sendTransactionLogic(transactionDto);
                transactionDtoMap.remove(transactionDto.getTransId());
                return Optional.of(transactionDto);
            }
        }
        log.info("Результат выполнения checkTransaction2 'transactionDto': {}.", transactionDto);
        return Optional.empty();
    }
}