package org.Isa4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.Isa4.dto.InformationAccountResponse;
import org.Isa4.exceptions.BadRequestException;
import org.Isa4.mapper.InformationAccountMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.PositionInstrument;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class InstrumentService {

    @Value("${kafka.delay.second}")
    private long KAFKA_DELAY_SECOND;


    private final InformationAccountRepository informationAccountRepository;


    private final PositionInstrumentService positionInstrumentService;

    private final ProducerLogic producerLogic;

    public List<PositionInstrument> info(InformationAccount dto) {
        informationAccountRepository.deleteAll();
        InformationAccount informationAccount = informationAccountRepository.save(dto);
        InformationAccountResponse informationAccountResponse = InformationAccountMapper.toDto(informationAccount);
        LocalDateTime ldt = producerLogic.sendInformationTool(informationAccountResponse);
        try {
            while (LocalDateTime.now().isBefore(ldt.plusSeconds(KAFKA_DELAY_SECOND))) {
                CompletableFuture<List<PositionInstrument>> listCompletableFuture = positionInstrumentService.getAllInstrumets();
                List<PositionInstrument> instrumentList = listCompletableFuture.get();
                if (!instrumentList.isEmpty()) {
                    return instrumentList;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException("Исключение в методе info в классе InstrumentService из-за задержки получения сообщения");
        }
        throw new BadRequestException("Исключение в методе info в классе InstrumentService из-за задержки получения сообщения");
    }
}
