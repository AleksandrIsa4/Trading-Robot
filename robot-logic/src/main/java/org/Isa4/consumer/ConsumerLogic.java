package org.Isa4.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.model.InformationTool;
import org.Isa4.model.PositionInstrument;
import org.Isa4.service.PositionInstrumentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerLogic {

    private static final String TOPIC_INFORMATION_TOOL = "informationTool";

    private static final String TOPIC_POSITION_INSTRUMENT = "positionInstrument";

    private final ObjectMapper objectMapper;

    private final PositionInstrumentService positionInstrumentService;

    @KafkaListener(topics = TOPIC_INFORMATION_TOOL)
    public void consumeInformationTool(String message) {
        try {
            InformationTool informationTool = objectMapper.readValue(message, InformationTool.class);
            log.info("consumeInformationTool message  {}", message);
            log.info("consumeInformationTool informationTool {}", informationTool);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    @Async
    @KafkaListener(topics = TOPIC_POSITION_INSTRUMENT)
    public void consumePositionInstrument(String message) {
        try {
            List<PositionInstrument> positionInstrumentList = objectMapper.readValue(message, new TypeReference<List<PositionInstrument>>() {
            });
            log.info("consumePositionInstrument message {}", message);
            log.info("consumePositionInstrument  informationTool {}", positionInstrumentList);
            positionInstrumentService.saveAllInstrumets(positionInstrumentList);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }


}
