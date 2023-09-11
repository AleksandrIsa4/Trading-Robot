package org.Isa4.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationAccountDto;
import org.Isa4.dto.ParamExAll;
import org.Isa4.dto.TransactionDto;
import org.Isa4.exceptions.BadRequestException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProducerLogic {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<Long, String> kafkaTemplate;

    private static final String TOPIC_PARAM = "paramEx";

    private static final String TOPIC_GETITEM = "getItem";

    private static final String TOPIC_TRANSACTION_DTO = "getTransactionDto";

    private static Long keySend = 1L;

    public void sendParam(@RequestBody ParamExAll dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_PARAM, json);
            future.addCallback(System.out::println, System.err::println);
            log.info("ProducerLogic sendParam dto  {}", dto);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }

    public LocalDateTime sendInformationTool(InformationAccountDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            SendResult<Long, String> sendResult = kafkaTemplate.send(TOPIC_GETITEM, json).get();
            LocalDateTime ldt = Instant.ofEpochMilli(sendResult.getRecordMetadata().timestamp())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            log.info("ProducerLogic sendInformationTool dto  {}", dto);
            return ldt;
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            throw new BadRequestException("Исключение в методе sendInformationToo в классе ProducerLogic");
        }
    }

    public void sendTransactionDto(@RequestBody List<TransactionDto> dtos) {
        try {
            String json = objectMapper.writeValueAsString(dtos);
            ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send(TOPIC_TRANSACTION_DTO, json);
            future.addCallback(System.out::println, System.err::println);
            log.info("ProducerLogic sendTransactionDto dto  {}", dtos);
        } catch (JsonProcessingException e) {
            System.out.println(e.toString());
        }
    }
}
