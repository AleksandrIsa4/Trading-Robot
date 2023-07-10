package com.enfernuz.quik.lua.rpc.producer;

import com.enfernuz.quik.lua.rpc.model.InformationTool;
import com.enfernuz.quik.lua.rpc.model.PositionInstrument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    public void sendInformationTool(@RequestBody InformationTool dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_INFORMATION_TOOL, json);
            future.addCallback(System.out::println, System.err::println);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    public void sendPositionInstrument(@RequestBody List<PositionInstrument> dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_POSITION_INSTRUMENT, json);
            future.addCallback(System.out::println, System.err::println);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }


}
