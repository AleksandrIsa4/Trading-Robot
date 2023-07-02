package com.enfernuz.quik.lua.rpc.producer;

import com.enfernuz.quik.lua.rpc.model.InformationTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProducerRpc {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<Long, String> kafkaTemplate;

    private static final String ORDER_TOPIC = "informationTool";

    public void sendInformationTool(@RequestBody InformationTool dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(ORDER_TOPIC, json);
            future.addCallback(System.out::println, System.err::println);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
