package org.Isa4.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationAccountRequest;
import org.Isa4.mapper.InformationAccountMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.ParamExAll;
import org.Isa4.model.PositionInstrument;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.service.InstrumentService;
import org.Isa4.service.TradeAkziiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("msg")
@RequiredArgsConstructor
public class KafkaController {

    private final ProducerLogic producerLogic;

    private final InstrumentService instrumentService;

    private final TradeAkziiService tradeAkziiService;

    @PostMapping
    public void sendParam(@RequestBody ParamExAll dto) {
        producerLogic.sendParam(dto);
    }

    // Получение информации о доступных активах в портфеле
    @PostMapping(value = "/information")
    public List<PositionInstrument> postInformationAccount(@RequestBody @NonNull InformationAccountRequest dto) {
        InformationAccount informationAccount = InformationAccountMapper.toEntity(dto);
        List<PositionInstrument> instruments = instrumentService.info(informationAccount);
        return instruments;
    }

    // Запись инструмента для торговли и первоначальные параметры
    @PostMapping(value = "/tradeAkzii")
    public TradeAkzii postRunTradeAkzii(@RequestBody @NonNull TradeAkzii dto) {
        return tradeAkziiService.saveTradeAkzii(dto);
    }
}