package org.Isa4.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.MainService;
import org.Isa4.dto.InformationAccountRequest;
import org.Isa4.dto.ParamExAll;
import org.Isa4.dto.TransactionDto;
import org.Isa4.mapper.InformationAccountMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.PositionInstrument;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.service.*;
import org.Isa4.service.impl.LogicServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("msg")
@RequiredArgsConstructor
public class KafkaController {

    private final ProducerLogic producerLogic;

    private final InstrumentService instrumentService;

    private final TradeAkziiService tradeAkziiService;

    private final PositionInstrumentService positionInstrumentService;

    private final MainService mainService;

    // Отправка параметров
    @PostMapping
    public void sendParam(@RequestBody ParamExAll dto) {
        producerLogic.sendParam(dto);
    }

    // Получение информации о доступных активах в портфеле
    @Async
    @PostMapping(value = "/information")
    public List<PositionInstrument> postInformationAccount(@RequestBody @NonNull InformationAccountRequest dto) {
        InformationAccount informationAccount = InformationAccountMapper.toEntityRequest(dto);
        instrumentService.saveAccount(informationAccount);
        log.info("KafkaController postInformationAccount  dto {}", dto);
        List<PositionInstrument> instruments = instrumentService.info(informationAccount);
        System.out.println("instruments");
        System.out.println(instruments);
        return instruments;
    }

    // Запись инструмента для торговли и первоначальные параметры
    @PostMapping(value = "/tradeAkzii")
    public TradeAkzii postRunTradeAkzii(@RequestBody @NonNull TradeAkzii dto) {
        log.info("KafkaController postRunTradeAkzii  dto {}", dto);
        return tradeAkziiService.saveTradeAkzii(dto);
    }

    // Получение доступных средств на аккаунте
    @GetMapping(value = "/money")
    public Float getMoney(@RequestParam String account) {
        log.info("KafkaController getMoney  account {}", account);
        return positionInstrumentService.infoMoney(account);
    }

    // Подача заявки
    @PostMapping(value = "/tradeOperation")
    public void postTradeOperation(@RequestBody @NonNull TransactionDto dto) {
        log.info("KafkaController postTradeOperation  dto {}", dto);
        positionInstrumentService.additionTransactionAccount(dto);
    }

    // TODO: Не используется
    @PostMapping(value = "/run")
    public void postTradeOperation() throws InterruptedException {
        log.info("KafkaController postTradeOperation");
        mainService.run();
    }

    // TODO: Не используется
    @Async
    @GetMapping(value = "/runRPC")
    public boolean getrunRPC() {
        log.info("KafkaController getrunRPC");
        //MainService.rpcRun=true;
        return true;
    }

    // TODO: Не используется
    @GetMapping(value = "/exitRPC")
    public void getexitRPC() {
        log.info("KafkaController getexitRPC");
    }
}