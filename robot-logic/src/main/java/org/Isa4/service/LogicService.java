package org.Isa4.service;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.Isa4.dto.ParamExAll;
import org.Isa4.dto.TransactionDto;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.InformationTool;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.Isa4.repository.InformationToolRepository;
import org.Isa4.repository.TradeAkziiRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogicService {

    @Value("${broker.commission}")
    private float BROKER_COMMISSION;

    @Value("${trade.win}")
    private float TRADE_WIN;

    @Setter
    private InformationAccount informationAccount;

    private final TradeAkziiRepository tradeAkziiRepository;

    private final InformationToolRepository informationToolRepository;

    private final InformationAccountRepository informationAccountRepository;

    private final Map<String, TradeAkzii> tradeAkziiMap;

    private final ProducerLogic producerLogic;

    private final List<Float> lastPrice;

    static long transId = System.currentTimeMillis();

    // Вычисление значений параметров для получения выигрыша равному TRADE_WIN
    // TODO: Не доделана
    @Async
    //@Scheduled(fixedRate = 5000)
    public void run() {
        List<TradeAkzii> tradeAkziiList = tradeAkziiRepository.findAll();
        InformationTool informationTool;
        // Средняя цена покупки/продажи на бирже в данный момент
        float averageChena;
        // Разница между покупкой и продажей основной операции в шагах акции
        long takeProfit;
        // Разница между покупкой и продажей дополнительной операции в шагах акции
        long takeProfitDop;
        // Количество шагов акций от продажи/покупки для выставления дополнительной операции
        long raznizaMinysSled;
        // Количество шагов акций от продажи/покупки для начала следующей основной операции
        long raznizaMinys;
        long lotSize;
        float secPriceStep;
        // Количество акций покупки\продажи при основной операции
        long quantityFirst;
        // Количество акций покупки\продажи при дополнительной операции
        long quantitySecond;
        // Разница количества акций покупки\продажи при дополнительной операции между основнойи дополнительной операцией
        long quantitySecondIntermediate;
        for (TradeAkzii tradeAkzii : tradeAkziiList) {
            tradeAkziiMap.put(tradeAkzii.getSecCode(), tradeAkzii);
            informationTool = informationToolRepository.findBySecCodeAndAndCreatedTimeIsAfter(tradeAkzii.getSecCode(), LocalDateTime.now().minusSeconds(1));
            averageChena = (informationTool.getOffer() + informationTool.getBid()) / 2;
            lotSize = tradeAkzii.getPositionInstrument().getLotSize();
            secPriceStep = tradeAkzii.getPositionInstrument().getSecPriceStep();
            takeProfit = (long) Math.ceil(TRADE_WIN + averageChena * lotSize * (1 + BROKER_COMMISSION) - averageChena * lotSize * (1 - BROKER_COMMISSION) / ((1 - BROKER_COMMISSION) * lotSize * secPriceStep));
            raznizaMinysSled = (long) Math.ceil(takeProfit / 2);
            raznizaMinys = raznizaMinysSled + 1;
            quantityFirst = tradeAkzii.getQuantityFirst();
            quantitySecond = tradeAkzii.getQuantitySecond();
            quantitySecondIntermediate = quantitySecond - quantityFirst;
            takeProfitDop = (long) Math.ceil((secPriceStep * (quantitySecond * (1 - BROKER_COMMISSION) - quantitySecondIntermediate * (1 + BROKER_COMMISSION)) * raznizaMinys - quantitySecond * averageChena * (1 - BROKER_COMMISSION) + TRADE_WIN / lotSize + quantityFirst * averageChena * (1 + BROKER_COMMISSION) + quantitySecondIntermediate * averageChena * (1 + BROKER_COMMISSION)) / (quantitySecondIntermediate * secPriceStep * (1 + BROKER_COMMISSION)));
            PairPriceBuySell pairPriceBuySell = momentPokypki(0, 99999, tradeAkzii, takeProfit);
            lastPrice.add(averageChena);
        }
        System.out.println(tradeAkziiMap);
    }

    // Вычисления цены покупки/продажи в зависимости от нижнего и верхнего ограничения цены
    // (2 секунды задержка для подтверждения, что цена резко не вернулась обратно)
    public PairPriceBuySell momentPokypki(float minPrice, float maxPrice, TradeAkzii tradeAkzii, float takeProfit) {
        PairPriceBuySell pairPriceBuySell = new PairPriceBuySell();
        double priceBuy;
        double priceSell;
        try {
            InformationTool informationTool = informationToolRepository.findBySecCodeAndAndCreatedTimeIsAfter(tradeAkzii.getSecCode(), LocalDateTime.now().minusSeconds(1));
            float averageChena = (informationTool.getOffer() + informationTool.getBid()) / 2;
            float secPriceStep = tradeAkzii.getPositionInstrument().getSecPriceStep();
            if (averageChena <= minPrice) {
                Thread.sleep(2000);
                informationTool = informationToolRepository.findBySecCodeAndAndCreatedTimeIsAfter(tradeAkzii.getSecCode(), LocalDateTime.now().minusSeconds(1));
                if (averageChena <= minPrice) {
                    //цена покупки: текущая цена покупки на бирже + разница между ценой продажи и покупки на бирже деленная на 2 - takeProfit деленный на 2 (цена покупки/продажи на количество шагов takeProfit/2 от текущей цены)
                    priceBuy = informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep - Math.floor(takeProfit / 2) * secPriceStep;
                    priceSell = informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep + Math.floor((takeProfit / 2) + 1) * secPriceStep;
                    pairPriceBuySell.setPriceBuy(priceBuy);
                    pairPriceBuySell.setPriceSell(priceSell);
                }
            }
            if (averageChena >= maxPrice) {
                Thread.sleep(2000);
                informationTool = informationToolRepository.findBySecCodeAndAndCreatedTimeIsAfter(tradeAkzii.getSecCode(), LocalDateTime.now().minusSeconds(1));
                if (averageChena >= maxPrice) {
                    //цена покупки: текущая цена покупки на бирже + разница между ценой продажи и покупки на бирже деленная на 2 - takeProfit деленный на 2 (цена покупки/продажи на количество шагов takeProfit/2 от текущей цены)
                    priceBuy = informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep - Math.floor(takeProfit / 2) * secPriceStep;
                    priceSell = informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep + Math.floor((takeProfit / 2) + 1) * secPriceStep;
                    pairPriceBuySell.setPriceBuy(priceBuy);
                    pairPriceBuySell.setPriceSell(priceSell);
                }
            }
        } catch (InterruptedException e) {
        }
        return pairPriceBuySell;
    }

    // Пара цены покупки/продажи
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    private class PairPriceBuySell {

        private double priceBuy;

        private double priceSell;
    }

    // TODO: Не доделана
    public void infoInstrument(ParamExAll paramExAll) {
        producerLogic.sendParam(paramExAll);
    }

    // TODO: Не доделана
    public void sendTransaction(TransactionDto transaction) {
        producerLogic.sendTransactionDto(transaction);
    }
}
