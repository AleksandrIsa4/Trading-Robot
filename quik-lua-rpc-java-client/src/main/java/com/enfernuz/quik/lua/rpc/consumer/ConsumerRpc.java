package com.enfernuz.quik.lua.rpc.consumer;

import com.enfernuz.quik.lua.rpc.dto.InformationAccountResponse;
import com.enfernuz.quik.lua.rpc.model.InformationTool;
import com.enfernuz.quik.lua.rpc.model.ParamExAll;
import com.enfernuz.quik.lua.rpc.model.PositionInstrument;
import com.enfernuz.quik.lua.rpc.producer.ProducerRpc;
import com.enfernuz.quik.lua.rpc.service.RpcService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
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

    private final ProducerRpc producerRpc;

    @KafkaListener(topics = TOPIC_PARAM)
    public void consumeParam(String message) {
        try {
            ParamExAll paramExAll = objectMapper.readValue(message, ParamExAll.class);
            log.info("consumeParam message {}", message);
            log.info("consumeParam message paramExAll {}", paramExAll);
            InformationTool informationTool = rpcService.infoParamEx(paramExAll);
            System.out.println(informationTool);
            producerRpc.sendInformationTool(informationTool);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    @KafkaListener(topics = TOPIC_GETITEM)
    public void consumeGetItem(String message) {
        try {
            InformationAccountResponse informationAccountResponse = objectMapper.readValue(message, InformationAccountResponse.class);
            log.info("consumeParam message {}", message);
            log.info("consumeParam message informationAccountResponse {}", informationAccountResponse);
            List<PositionInstrument> positionInstrumentList = rpcService.infoPositionInstrument(informationAccountResponse);
            producerRpc.sendPositionInstrument(positionInstrumentList);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
