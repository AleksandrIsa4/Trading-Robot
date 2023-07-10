package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.model.TradeAkzii;
import org.Isa4.repository.TradeAkziiRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeAkziiService {

    private final TradeAkziiRepository tradeAkziiRepository;

    public TradeAkzii saveTradeAkzii(TradeAkzii tradeAkzii) {
        return tradeAkziiRepository.save(tradeAkzii);
    }
}
