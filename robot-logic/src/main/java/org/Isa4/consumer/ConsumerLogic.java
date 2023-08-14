package org.Isa4.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationToolDto;
import org.Isa4.dto.MoneyInfo;
import org.Isa4.dto.PositionInstrumentDto;
import org.Isa4.service.InstrumentService;
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

    private static final String TOPIC_MONEY_INFO = "moneyInfo";

    private final ObjectMapper objectMapper;

    private final PositionInstrumentService positionInstrumentService;

    private final InstrumentService instrumentService;

    @KafkaListener(topics = TOPIC_INFORMATION_TOOL)
    public void consumeInformationTool(String message) {
        try {
            InformationToolDto informationToolDto = objectMapper.readValue(message, InformationToolDto.class);
            log.info("consumeInformationTool message  {}", message);
            log.info("consumeInformationTool informationTool {}", informationToolDto);
            positionInstrumentService.saveInformationTool(informationToolDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    @Async
    @KafkaListener(topics = TOPIC_POSITION_INSTRUMENT)
    public void consumePositionInstrument(String message) {
        try {
            List<PositionInstrumentDto> positionInstrumentList = objectMapper.readValue(message, new TypeReference<List<PositionInstrumentDto>>() {
            });
            log.info("consumePositionInstrument message {}", message);
            log.info("consumePositionInstrument  informationTool {}", positionInstrumentList);
            positionInstrumentService.saveAllInstrumets(positionInstrumentList);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    @Async
    @KafkaListener(topics = TOPIC_MONEY_INFO)
    public void consumeMoneyInfo(String message) {
        try {
            MoneyInfo moneyInfo = objectMapper.readValue(message, new TypeReference<MoneyInfo>() {
            });
            log.info("consumePositionInstrument message {}", message);
            log.info("consumePositionInstrument  informationTool {}", moneyInfo);
            instrumentService.saveMoney(moneyInfo);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
