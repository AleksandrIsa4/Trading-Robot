package com.enfernuz.quik.lua.rpc.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationToolDto;
import org.Isa4.dto.MoneyInfo;
import org.Isa4.dto.PositionInstrumentDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProducerRpc {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<Long, String> kafkaTemplate;

    private static final String TOPIC_INFORMATION_TOOL = "informationTool";

    private static final String TOPIC_POSITION_INSTRUMENT = "positionInstrument";

    private static final String TOPIC_MONEY_INFO = "moneyInfo";

    private static final String TOPIC_TRANSACTION_DTO = "getTransactionDto";

    public void sendInformationTool(@RequestBody InformationToolDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_INFORMATION_TOOL, json);
            future.addCallback(System.out::println, System.err::println);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    public void sendPositionInstrument(@RequestBody List<PositionInstrumentDto> dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_POSITION_INSTRUMENT, json);
            future.addCallback(System.out::println, System.err::println);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    public void sendMoneyInfo(@RequestBody MoneyInfo moneyInfo) {
        try {
            String json = objectMapper.writeValueAsString(moneyInfo);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_MONEY_INFO, json);
            future.addCallback(System.out::println, System.err::println);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

}
