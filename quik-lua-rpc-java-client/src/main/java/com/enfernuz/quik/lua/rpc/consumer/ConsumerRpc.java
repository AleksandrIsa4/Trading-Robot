package com.enfernuz.quik.lua.rpc.consumer;

import com.enfernuz.quik.lua.rpc.model.InformationTool;
import com.enfernuz.quik.lua.rpc.model.ParamExAll;
import com.enfernuz.quik.lua.rpc.producer.ProducerRpc;
import com.enfernuz.quik.lua.rpc.service.RpcService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerRpc {

    private final ObjectMapper objectMapper;

    private final RpcService rpcService;

    private static final String ORDER_TOPIC = "paramEx";

    private final ProducerRpc producerRpc;

    @KafkaListener(topics = ORDER_TOPIC)
    public void consumeMessage(String message) {
        try {
            ParamExAll paramExAll = objectMapper.readValue(message, ParamExAll.class);
            log.info("message consumed {}", message);
            log.info("message paramExAll {}", paramExAll);
            InformationTool informationTool = rpcService.infoParamEx(paramExAll);
            System.out.println(informationTool);
            producerRpc.sendInformationTool(informationTool);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
