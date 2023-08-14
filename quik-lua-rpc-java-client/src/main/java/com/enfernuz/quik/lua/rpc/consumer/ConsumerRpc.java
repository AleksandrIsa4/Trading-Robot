package com.enfernuz.quik.lua.rpc.consumer;

import com.enfernuz.quik.lua.rpc.producer.ProducerRpc;
import com.enfernuz.quik.lua.rpc.service.RpcService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerRpc {

    private final ObjectMapper objectMapper;

    private final RpcService rpcService;

    private static final String TOPIC_PARAM = "paramEx";

    private static final String TOPIC_GETITEM = "getItem";

    private static final String TOPIC_TRANSACTION_DTO = "getTransactionDto";

    private final ProducerRpc producerRpc;

    @KafkaListener(topics = TOPIC_PARAM)
    public void consumeParam(String message) {
        try {
            ParamExAll paramExAll = objectMapper.readValue(message, ParamExAll.class);
            log.info("consumeParam message {}", message);
            log.info("consumeParam message paramExAll {}", paramExAll);
            InformationToolDto informationToolDto = rpcService.infoParamEx(paramExAll);
            System.out.println(informationToolDto);
            producerRpc.sendInformationTool(informationToolDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    @KafkaListener(topics = TOPIC_GETITEM)
    public void consumeGetItem(String message) {
        try {
            InformationAccountDto informationToolDto = objectMapper.readValue(message, InformationAccountDto.class);
            log.info("consumeParam message {}", message);
            log.info("consumeParam message informationAccountResponse {}", informationToolDto);
            List<PositionInstrumentDto> positionInstrumentList = rpcService.infoPositionInstrument(informationToolDto);
            producerRpc.sendPositionInstrument(positionInstrumentList);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    @KafkaListener(topicPartitions =@TopicPartition(topic = TOPIC_TRANSACTION_DTO, partitions = "3"))
    public void consumeTransactionDto(String message) {
        try {
            TransactionDto transactionDto = objectMapper.readValue(message, TransactionDto.class);
            log.info("consumeParam message {}", message);
            log.info("consumeParam message transactionDto {}", transactionDto);
            String resultTransaction = rpcService.sendTrade(transactionDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
