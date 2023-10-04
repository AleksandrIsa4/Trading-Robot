package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.InformationToolDto;
import org.Isa4.dto.PositionInstrumentDto;
import org.Isa4.dto.TransactionDto;
import org.Isa4.exceptions.DataNotFoundException;
import org.Isa4.mapper.InformationToolMapper;
import org.Isa4.mapper.PositionInstrumentMapper;
import org.Isa4.mapper.TransactionMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.InformationTool;
import org.Isa4.model.PositionInstrument;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.Isa4.repository.InformationToolRepository;
import org.Isa4.repository.PositionInstrumentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionInstrumentService {

    private final PositionInstrumentRepository positionInstrumentRepository;

    private final InformationAccountRepository informationAccountRepository;

    private final InformationToolRepository informationToolRepository;

    private final ProducerLogic producerLogic;

    private static long sumRecord=0;

    // Сохранить все доступные бумаги
    @Async
    @Transactional
    public void saveAllInstrumets(List<PositionInstrumentDto> instruments) {
        log.info("PositionInstrumentService saveAllInstrumets  instruments {}", instruments);
        List<PositionInstrument> instrumentList = instruments.stream()
                .map(PositionInstrumentMapper::toEntity)
                .collect(Collectors.toList());
        positionInstrumentRepository.saveAll(instrumentList);
    }

    // Получить все доступные бумаги
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<PositionInstrument>> getAllInstrumets() {
        log.info("PositionInstrumentService getAllInstrumets");
        List<PositionInstrument> positionInstrumentList = positionInstrumentRepository.findAll();
        return CompletableFuture.completedFuture(positionInstrumentList);
    }

    // Получить доступных средств на счету
    @Transactional(readOnly = true)
    public Float infoMoney(String account) {
        log.info("PositionInstrumentService infoMoney  account {}", account);
        InformationAccount informationAccount = informationAccountRepository.findById(account).orElseThrow(() -> new DataNotFoundException("Нет данного аккаунта"));
        return informationAccount.getMoney();
    }

    @Transactional
    // Сохранить информацию об инструменте для дальнейшего использования
    public void saveInformationTool(InformationToolDto informationToolDto) {
        log.info("PositionInstrumentService saveInformationTool  informationToolDto {}", informationToolDto);
        if(sumRecord>=40){
            informationToolRepository.deleteByCreatedTimeIsBefore(LocalDateTime.now().minusSeconds(5));
            sumRecord=0;
        }
        InformationTool informationTool = InformationToolMapper.toEntity(informationToolDto);
        informationToolRepository.save(informationTool);
        sumRecord++;
    }

    // Дополнить заявку необходимой информацией о счете
    public void additionTransactionAccount(TransactionDto dto) {
        log.info("PositionInstrumentService additionTransactionAccount  dto {}", dto);
        InformationAccount informationAccount = informationAccountRepository.findFirstByOrderByAccountDesc().orElseThrow(() -> new DataNotFoundException("Нет актуальной информации в БД по аккаунту"));
        ;
        TransactionMapper.toAdditionAccount(dto, informationAccount);
        producerLogic.sendTransactionDto(List.of(dto));
    }
}
