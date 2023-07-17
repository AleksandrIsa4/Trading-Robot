package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.dto.MoneyInfo;
import org.Isa4.dto.PositionInstrumentDto;
import org.Isa4.exceptions.DataNotFoundException;
import org.Isa4.mapper.PositionInstrumentMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.PositionInstrument;
import org.Isa4.repository.InformationAccountRepository;
import org.Isa4.repository.PositionInstrumentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionInstrumentService {

    private final PositionInstrumentRepository positionInstrumentRepository;

    private final InformationAccountRepository informationAccountRepository;

    @Async
    @Transactional
    public void saveAllInstrumets(List<PositionInstrumentDto> instruments) {
        List<PositionInstrument> instrumentList=instruments.stream()
                .map(PositionInstrumentMapper::toEntity)
                .collect(Collectors.toList());
        positionInstrumentRepository.saveAll(instrumentList);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<PositionInstrument>> getAllInstrumets() {
        List<PositionInstrument> positionInstrumentList = positionInstrumentRepository.findAll();
        return CompletableFuture.completedFuture(positionInstrumentList);
    }

    @Transactional(readOnly = true)
    public Float infoMoney(String account) {
        InformationAccount informationAccount = informationAccountRepository.findById(account).orElseThrow(() -> new DataNotFoundException("Нет данного аккаунта"));
        return informationAccount.getMoney();
    }
}
