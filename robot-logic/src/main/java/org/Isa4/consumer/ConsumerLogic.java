package org.Isa4.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.model.InformationTool;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerLogic {

    private static final String ORDER_TOPIC = "informationTool";

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = ORDER_TOPIC)
    public void consumeMessage(String message) {
        try {
            InformationTool informationTool = objectMapper.readValue(message, InformationTool.class);
            log.info("message consumed {}", message);
            log.info("message informationTool {}", informationTool);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
