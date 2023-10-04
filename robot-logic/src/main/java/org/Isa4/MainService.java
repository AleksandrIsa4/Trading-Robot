package org.Isa4;

import lombok.RequiredArgsConstructor;
import org.Isa4.client.RunClient;
import org.Isa4.dto.ParamExAll;
import org.Isa4.exceptions.DataNotFoundException;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.PositionInstrument;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.Isa4.repository.PositionInstrumentRepository;
import org.Isa4.repository.TradeAkziiRepository;
import org.Isa4.service.InstrumentService;
import org.Isa4.service.KafkaService;
import org.Isa4.service.impl.LogicServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class MainService implements CommandLineRunner {

    private final InstrumentService instrumentService;

    private final InformationAccountRepository informationAccountRepository;

    private final PositionInstrumentRepository positionInstrumentRepository;

    private final TradeAkziiRepository tradeAkziiRepository;

    //@Qualifier("logicServiceFirst")
    @Qualifier("logicTestService")
    private final LogicServiceImpl logicService;

    private final ProducerLogic producerLogic;

    private final KafkaService kafkaService;

    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        kafkaService.createTopics();
        LocalDateTime ldt = LocalDateTime.now();
        while (!KafkaService.rpcRun.get() || KafkaService.rpcRun == null) {
            System.out.println("Rpc модуль не подключен");
            if (LocalDateTime.now().isAfter(ldt.plusSeconds(30))) {
                System.out.println("Rpc модуль не подключился");
                System.exit(-1);
            }
            Thread.sleep(1000);
        }
        boolean isExist = informationAccountRepository.existsAllBy();
        if (!isExist) {
            System.out.println("Необходимо отправить информацию об аккаунте");
            while (!isExist) {
                isExist = informationAccountRepository.existsAllBy();
                Thread.sleep(1000);
            }
        }
        InformationAccount informationAccount = informationAccountRepository.findFirstByOrderByAccountDesc().orElseThrow(() -> new DataNotFoundException("Нет актуальной информации в БД по аккаунту"));
        instrumentService.info(informationAccount);
        isExist = positionInstrumentRepository.existsAllBy();
        if (!isExist) {
            System.out.println("Необходимо купить акции для торговли");
            while (!isExist) {
                isExist = positionInstrumentRepository.existsAllBy();
                Thread.sleep(1000);
            }
        }
        System.out.println("Доступные акции для торговли");
        List<PositionInstrument> positionInstrumentList = positionInstrumentRepository.findAll();
        System.out.println(positionInstrumentList);

        isExist = tradeAkziiRepository.existsAllBy();
        if (!isExist) {
            System.out.println("Необходимо задать параметры работы робота");
            while (!isExist) {
                isExist = tradeAkziiRepository.existsAllBy();
                Thread.sleep(1000);
            }
        }
        System.out.println("Параметры работы робота");
        List<TradeAkzii> tradeAkziiList = tradeAkziiRepository.findAll();
        System.out.println(tradeAkziiList);

        tradeAkziiList.forEach(tradeAkzii -> {
            ParamExAll paramExAll = new ParamExAll(tradeAkzii.getPk().getClassCode(), tradeAkzii.getPk().getSecCode());
            producerLogic.sendParam(paramExAll);
        });
        logicService.run();
    }
}