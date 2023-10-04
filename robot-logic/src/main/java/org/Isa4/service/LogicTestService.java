package org.Isa4.service;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.ParamExAll;
import org.Isa4.dto.RunStatus;
import org.Isa4.dto.TransactionDto;
import org.Isa4.dto.constant.ModulConstans;
import org.Isa4.exceptions.BadRequestException;
import org.Isa4.exceptions.DataNotFoundException;
import org.Isa4.mapper.TransactionMapper;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.InformationTool;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.Isa4.repository.InformationToolRepository;
import org.Isa4.repository.TradeAkziiRepository;
import org.Isa4.service.impl.LogicServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


// Тестовый класс для максимально быстрого выполнения заявок
@Slf4j
@Service("logicTestService")
@RequiredArgsConstructor
public class LogicTestService implements LogicServiceImpl {

    @Value("${broker.commission}")
    private float BROKER_COMMISSION;

    @Value("${trade.win}")
    private float TRADE_WIN;

    private long KAFKA_DELAY_SECOND = ModulConstans.DELAY_TIME;

    @Setter
    private InformationAccount informationAccount;

    private final TradeAkziiRepository tradeAkziiRepository;

    private final InformationToolRepository informationToolRepository;

    private final InformationAccountRepository informationAccountRepository;

    private final Map<String, TradeAkzii> tradeAkziiMap;

    private final ProducerLogic producerLogic;

    private final List<Float> lastPrice;

    static long transId = System.currentTimeMillis();

    // Средняя цена покупки/продажи на бирже в данный момент
    private float averageChena;
    // Разница между покупкой и продажей основной операции в шагах акции
    private long takeProfit;
    // Разница между покупкой и продажей дополнительной операции в шагах акции
    private long takeProfitDop;
    // Количество шагов акций от продажи/покупки для выставления дополнительной операции
    private long raznizaMinysSled;
    // Количество шагов акций от продажи/покупки для начала следующей основной операции
    private long raznizaMinys;
    private long lotSize;
    private float secPriceStep;
    // Количество акций покупки\продажи при основной операции
    private long quantityFirst;
    // Количество акций покупки\продажи при дополнительной операции
    private long quantitySecond;
    // Разница количества акций покупки\продажи при дополнительной операции между основнойи дополнительной операцией
    private long quantitySecondIntermediate;

    // Вычисление значений параметров для получения выигрыша равному TRADE_WIN
    // TODO: Не доделана
    @Override
    //@Scheduled(fixedRate = 5000)
    public void run() {
        log.info("LogicTestService run");
        informationAccount = informationAccountRepository.findFirstByOrderByAccountDesc().orElseThrow(() -> new DataNotFoundException("Нет актуальной информации в БД по аккаунту"));
        List<TradeAkzii> tradeAkziiList = tradeAkziiRepository.findAll();
        InformationTool informationTool;
        for (TradeAkzii tradeAkzii : tradeAkziiList) {
            tradeAkziiMap.put(tradeAkzii.getPk().getSecCode(), tradeAkzii);
            informationTool = getInformationToolNow(tradeAkzii.getPk().getSecCode());
            calculation(tradeAkzii, informationTool);
            PairPriceBuySell pairPriceBuySell = momentPokypki(99999, 0, tradeAkzii);
            TransactionDto transBuy = tradeNewOrder(pairPriceBuySell.getPriceBuy(), "B", tradeAkzii.getQuantityFirst(), informationTool);
            TransactionDto transSell = tradeNewOrder(pairPriceBuySell.getPriceSell(), "S", tradeAkzii.getQuantityFirst(), informationTool);
            producerLogic.sendTransactionDto(List.of(transBuy, transSell));
            lastPrice.add(averageChena);
        }
        System.out.println(tradeAkziiMap);
    }

    private TransactionDto tradeNewOrder(float price, String operation, long quantity, InformationTool informationTool) {
        TransactionDto transaction = TransactionDto.builder()
                .action("NEW_ORDER")
                .classCode(informationTool.getClassCode())
                .secCode(informationTool.getSecCode())
                .operation(operation)
                .type("L")
                .quantity(quantity)
                .price(price)
                .build();
        return TransactionMapper.toAdditionAccount(transaction, informationAccount);
    }

    private InformationTool getInformationToolNow(String secCode) {
        LocalDateTime ldt = LocalDateTime.now();
        try {
            while (LocalDateTime.now().isBefore(ldt.plusSeconds(KAFKA_DELAY_SECOND)) && RunStatus.logicRun.get()) {
                Optional<InformationTool> informationTool = informationToolRepository.findTop1BySecCodeAndAndCreatedTimeIsAfterOrderByCreatedTimeAsc(secCode, LocalDateTime.now().minusSeconds(1));
                if (!informationTool.isEmpty()) {
                    return informationTool.get();
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.getMessage();
            System.exit(1);
        }
        throw new BadRequestException("Исключение в методе getInformationToolNow в классе LogicService из-за задержки получения сообщения");
    }

    private void calculation(TradeAkzii tradeAkzii, InformationTool informationTool) {
        averageChena = (informationTool.getOffer() + informationTool.getBid()) / 2;
        log.info("LogicService calculation  getOffer={}, getBid={}, averageChena={}", informationTool.getOffer(), informationTool.getBid(), averageChena);
        lotSize = tradeAkzii.getPositionInstrument().getLotSize();
        secPriceStep = tradeAkzii.getPositionInstrument().getSecPriceStep();
        takeProfit = 3;
        raznizaMinysSled = (long) Math.ceil(takeProfit / 2);
        raznizaMinys = raznizaMinysSled + 1;
        quantityFirst = tradeAkzii.getQuantityFirst();
        quantitySecond = tradeAkzii.getQuantitySecond();
        quantitySecondIntermediate = quantitySecond - quantityFirst;
        takeProfitDop = 4;
    }

    // Вычисления цены покупки/продажи в зависимости от нижнего и верхнего ограничения цены
    // (2 секунды задержка для подтверждения, что цена не вернулась обратно)
    public PairPriceBuySell momentPokypki(float minPrice, float maxPrice, TradeAkzii tradeAkzii) {
        log.info("LogicService momentPokypki  minPrice={}, maxPrice={}, tradeAkzii={}", minPrice, maxPrice, tradeAkzii);
        PairPriceBuySell pairPriceBuySell = new PairPriceBuySell();
        float priceBuy;
        float priceSell;
        try {
            InformationTool informationTool = getInformationToolNow(tradeAkzii.getPk().getSecCode());
            float averageChena = (informationTool.getOffer() + informationTool.getBid()) / 2;
            float secPriceStep = tradeAkzii.getPositionInstrument().getSecPriceStep();
            if (averageChena <= minPrice) {
                Thread.sleep(2000);
                informationTool = getInformationToolNow(tradeAkzii.getPk().getSecCode());
                if (averageChena <= minPrice) {
                    //цена покупки: текущая цена покупки на бирже + разница между ценой продажи и покупки на бирже деленная на 2 - takeProfit деленный на 2 (цена покупки/продажи на количество шагов takeProfit/2 от текущей цены)
                    priceBuy = (float) (informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep - Math.floor(takeProfit / 2) * secPriceStep);
                    priceSell = (float) (informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep + Math.floor((takeProfit / 2) + 1) * secPriceStep);
                    Rounding(priceBuy);
                    Rounding(priceSell);
                    pairPriceBuySell.setPriceBuy(priceBuy);
                    pairPriceBuySell.setPriceSell(priceSell);
                }
            }
            if (averageChena >= maxPrice) {
                Thread.sleep(2000);
                informationTool = getInformationToolNow(tradeAkzii.getPk().getSecCode());
                if (averageChena >= maxPrice) {
                    //цена покупки: текущая цена покупки на бирже + разница между ценой продажи и покупки на бирже деленная на 2 - takeProfit деленный на 2 (цена покупки/продажи на количество шагов takeProfit/2 от текущей цены)
                    priceBuy = (float) (informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep - Math.floor(takeProfit / 2) * secPriceStep);
                    priceSell = (float) (informationTool.getBid() + Math.floor(((informationTool.getOffer() - informationTool.getBid()) / 2) / secPriceStep) * secPriceStep + Math.floor((takeProfit / 2) + 1) * secPriceStep);
                    Rounding(priceBuy);
                    Rounding(priceSell);
                    pairPriceBuySell.setPriceBuy(priceBuy);
                    pairPriceBuySell.setPriceSell(priceSell);
                }
            }
        } catch (InterruptedException e) {
            e.getMessage();
        }
        return pairPriceBuySell;
    }

    private float Rounding(float price) {
        log.info("LogicService Rounding  price={}", price);
        int accuracy = BigDecimal.valueOf(secPriceStep).scale();
        BigDecimal price2 = BigDecimal.valueOf(price).setScale(accuracy, RoundingMode.HALF_DOWN);
        log.info("LogicService Rounding  price2={}", price2.floatValue());
        return price2.floatValue();
    }


    // Пара цены покупки/продажи
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    private class PairPriceBuySell {

        float priceBuy;

        float priceSell;
    }

    // TODO: Не доделана
    public void infoInstrument(ParamExAll paramExAll) {
        producerLogic.sendParam(paramExAll);
    }

    // TODO: Не доделана
    public void sendTransaction(TransactionDto transaction) {
        //      producerLogic.sendTransactionDto(transaction);
    }
}
