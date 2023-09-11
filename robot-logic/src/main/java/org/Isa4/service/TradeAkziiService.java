package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.model.TradeAkzii;
import org.Isa4.repository.TradeAkziiRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeAkziiService {

    private final TradeAkziiRepository tradeAkziiRepository;

    public TradeAkzii saveTradeAkzii(TradeAkzii tradeAkzii) {
        log.info("TradeAkziiService saveTradeAkzii  tradeAkzii {}", tradeAkzii);
        return tradeAkziiRepository.save(tradeAkzii);
    }
}
