package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.dto.ParamExAll;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.TradeAkziiRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogicService {

    private final TradeAkziiRepository tradeAkziiRepository;

    private final Map<String,TradeAkzii> tradeAkziiMap;

    private final ProducerLogic producerLogic;

    @Async
    @Scheduled(fixedRate = 5000)
    public void run() {
        List<TradeAkzii> tradeAkziiList = tradeAkziiRepository.findAll();
        for (TradeAkzii tradeAkzii : tradeAkziiList) {
            tradeAkziiMap.put(tradeAkzii.getSecCode(),tradeAkzii);
        }
        System.out.println(tradeAkziiMap);
    //    producerLogic.sendParam(paramExAll);
    }

    public void infoInstrumenta(ParamExAll paramExAll){
   //     producerLogic.sendParam(paramExAll);
    }
}
