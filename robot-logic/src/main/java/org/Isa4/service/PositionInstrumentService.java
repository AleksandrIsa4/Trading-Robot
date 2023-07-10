package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.model.PositionInstrument;
import org.Isa4.repository.PositionInstrumentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PositionInstrumentService {

    private final PositionInstrumentRepository positionInstrumentRepository;

    public void saveAllInstrumets(List<PositionInstrument> instruments) {
        positionInstrumentRepository.saveAll(instruments);
    }

    @Async
    public CompletableFuture<List<PositionInstrument>> getAllInstrumets() {
        List<PositionInstrument> positionInstrumentList = positionInstrumentRepository.findAll();
        return CompletableFuture.completedFuture(positionInstrumentList);
    }
}