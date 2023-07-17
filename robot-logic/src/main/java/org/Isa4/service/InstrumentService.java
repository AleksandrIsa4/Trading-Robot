package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.dto.InformationAccountDto;
import org.Isa4.dto.MoneyInfo;
import org.Isa4.exceptions.BadRequestException;
import org.Isa4.mapper.InformationAccountMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.PositionInstrument;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Transactional
    public List<PositionInstrument> info(InformationAccount dtoAccount) {
        InformationAccountDto informationAccountDto = InformationAccountMapper.toDto(dtoAccount);
        LocalDateTime ldt = producerLogic.sendInformationTool(informationAccountDto);
        try {
            while (LocalDateTime.now().isBefore(ldt.plusSeconds(KAFKA_DELAY_SECOND))) {
                CompletableFuture<List<PositionInstrument>> listCompletableFuture = positionInstrumentService.getAllInstrumets();
                List<PositionInstrument> instrumentList = listCompletableFuture.get();
                if (!instrumentList.isEmpty()) {
                    System.out.println("instrumentList");
                    System.out.println(instrumentList);
                    return instrumentList;
                }
                    Thread.sleep(100);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException("Исключение в методе info в классе InstrumentService из-за ошибки");
        }
        throw new BadRequestException("Исключение в методе info в классе InstrumentService из-за задержки получения сообщения");
    }

    public void saveAccount(InformationAccount dtoAccount) {
        informationAccountRepository.save(dtoAccount);
    }

    @Transactional
    public void saveMoney(MoneyInfo moneyInfo) {
        informationAccountRepository.saveMoney(moneyInfo.getMoney(), moneyInfo.getFirmId(), moneyInfo.getTagMoney(), moneyInfo.getClientCode());
    }
}
