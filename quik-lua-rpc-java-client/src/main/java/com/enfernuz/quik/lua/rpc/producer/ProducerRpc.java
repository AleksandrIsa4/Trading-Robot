package com.enfernuz.quik.lua.rpc.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationToolDto;
import org.Isa4.dto.MoneyInfo;
import org.Isa4.dto.PositionInstrumentDto;
import org.Isa4.dto.TransactionDto;
import org.Isa4.dto.enumeration.Topics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
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

    // Отправка информации об инструменте
    public void sendInformationTool(@RequestBody InformationToolDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(Topics.Constants.TOPIC_INFORMATION_TOOL, json);
            future.addCallback(System.out::println, System.err::println);
            log.info("ProducerRpc sendInformationTool  dto {}", dto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Отправка списка доступных инструментов
    public void sendPositionInstrument(@RequestBody List<PositionInstrumentDto> dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(Topics.Constants.TOPIC_POSITION_INSTRUMENT, json);
            future.addCallback(System.out::println, System.err::println);
            log.info("ProducerRpc sendPositionInstrument  dto {}", dto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Отправка информации о доступных денежных средств
    public void sendMoneyInfo(@RequestBody MoneyInfo moneyInfo) {
        try {
            String json = objectMapper.writeValueAsString(moneyInfo);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(Topics.Constants.TOPIC_MONEY_INFO, json);
            future.addCallback(System.out::println, System.err::println);
            log.info("ProducerRpc sendMoneyInfo  moneyInfo {}", moneyInfo);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    // Отправка сообщения, что модуль работает
    @Scheduled(fixedRate = 2000)
    public void sendRpcRun() {
        log.info("ProducerRpc sendRpcRun");
        ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(Topics.Constants.TOPIC_RPC_RUN, "RPC_RUN");
        future.addCallback(System.out::println, System.err::println);
    }

    // Отправка информации о состоянии транзакции
    public void sendTransactionLogic(@RequestBody TransactionDto transactionDto) {
        try {
            String json = objectMapper.writeValueAsString(transactionDto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(Topics.Constants.TOPIC_TRANSACTION_LOGIC, json);
            future.addCallback(System.out::println, System.err::println);
            log.info("ProducerRpc sendTransactionDtoLogic transactionDto {}", transactionDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
