package com.enfernuz.quik.lua.rpc.consumer;

import com.enfernuz.quik.lua.rpc.producer.ProducerRpc;
import com.enfernuz.quik.lua.rpc.service.KafkaService;
import com.enfernuz.quik.lua.rpc.service.RpcService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.*;
import org.Isa4.dto.enumeration.Topics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerRpc {

    private final ObjectMapper objectMapper;

    private final RpcService rpcService;

    private final ProducerRpc producerRpc;

    // Запрос на информацию об инструменте
    @KafkaListener(topics = Topics.Constants.TOPIC_PARAM)
    public void consumeParam(String message) {
        try {
            ParamExAll paramExAll = objectMapper.readValue(message, ParamExAll.class);
            log.info("ConsumerRpc consumeParam paramExAll {}", paramExAll);
            InformationToolDto informationToolDto = rpcService.infoParamEx(paramExAll);
            System.out.println(informationToolDto);
            producerRpc.sendInformationTool(informationToolDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Запрос на информацию о доступных акциях
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_GETITEM)
    public void consumeGetItem(String message) {
        try {
            InformationAccountDto informationToolDto = objectMapper.readValue(message, InformationAccountDto.class);
            log.info("ConsumerRpc consumeGetItem informationToolDto {}", informationToolDto);
            List<PositionInstrumentDto> positionInstrumentList = rpcService.infoPositionInstrument(informationToolDto);
            producerRpc.sendPositionInstrument(positionInstrumentList);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Запрос на выполнение транзакции
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_TRANSACTION_DTO)
    public void consumeTransactionDto(String message) {
        try {
            List<TransactionDto> dtos = objectMapper.readValue(message, new TypeReference<List<TransactionDto>>() {
            });
            log.info("ConsumerRpc consumeTransactionDto dtos {}", dtos);
            rpcService.parseTrade(dtos);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Сообщение, что модуль robot-logic работает
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_LOGIC_RUN)
    public void consumeLogicRun(String message) {
        KafkaService.logicRun.set(true);
        log.info("ConsumerRpc consumeLogicRun message {}", message);
    }
}
