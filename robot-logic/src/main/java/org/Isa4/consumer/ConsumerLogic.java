package org.Isa4.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationToolDto;
import org.Isa4.dto.MoneyInfo;
import org.Isa4.dto.PositionInstrumentDto;
import org.Isa4.dto.TransactionDto;
import org.Isa4.dto.enumeration.Topics;
import org.Isa4.mapper.TransactionMapper;
import org.Isa4.model.TransactionTrade;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.service.InstrumentService;
import org.Isa4.service.KafkaService;
import org.Isa4.service.PositionInstrumentService;
import org.Isa4.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerLogic {

    private final ObjectMapper objectMapper;

    private final PositionInstrumentService positionInstrumentService;

    private final InstrumentService instrumentService;

    private final TransactionService transactionService;

    // Получение информации об инструменте
    @Async
//    @KafkaListener(topics = TopicsKafka2.TOPIC_INFORMATION_TOOL, autoStartup = "false", containerFactory = "kafkaListenerContainerFactory")
    @KafkaListener(topics = Topics.Constants.TOPIC_INFORMATION_TOOL)
    public void consumeInformationTool(String message) {
        try {
            InformationToolDto informationToolDto = objectMapper.readValue(message, InformationToolDto.class);
            log.info("ConsumerLogic consumeInformationTool informationTool {}", informationToolDto);
            positionInstrumentService.saveInformationTool(informationToolDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Получение информации о доступных инструментах
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_POSITION_INSTRUMENT)
    public void consumePositionInstrument(String message) {
        try {
            log.info("ConsumerLogic consumePositionInstrument");
            List<PositionInstrumentDto> positionInstrumentList = objectMapper.readValue(message, new TypeReference<List<PositionInstrumentDto>>() {
            });
            log.info("ConsumerLogic consumePositionInstrument  informationTool {}", positionInstrumentList);
            positionInstrumentService.saveAllInstrumets(positionInstrumentList);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Получение информации об доступных денежных средств
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_MONEY_INFO)
    public void consumeMoneyInfo(String message) {
        try {
            MoneyInfo moneyInfo = objectMapper.readValue(message, new TypeReference<MoneyInfo>() {
            });
            log.info("ConsumerLogic consumePositionInstrument  informationTool {}", moneyInfo);
            instrumentService.saveMoney(moneyInfo);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Получение информации о статусе работы модуля quik-lua-rpc-jaca-client
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_RPC_RUN)
    public void consumeRpcRun(String message) {
        KafkaService.rpcRun.set(true);
        log.info("ConsumerLogic consumeRpcRun message {}", message);
    }

    // Получение информации о статусе заявки
    @Async
    @KafkaListener(topics = Topics.Constants.TOPIC_TRANSACTION_LOGIC)
    public void consumeTransactionLogic(String message) {
        try {
            log.info("ConsumerLogic consumeTransactionLogic message {}", message);
            TransactionDto dto = objectMapper.readValue(message, TransactionDto.class);
            log.info("ConsumerLogic consumeTransactionLogic  transactionTrade {}", dto);
            TransactionTrade transactionTrade = TransactionMapper.toEntity(dto);
            transactionService.save(transactionTrade);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
