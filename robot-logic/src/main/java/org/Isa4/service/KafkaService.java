package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.ParamExAll;
import org.Isa4.dto.enumeration.Topics;
import org.Isa4.exceptions.DataNotFoundException;
import org.Isa4.model.InformationAccount;
import org.Isa4.model.PositionInstrument;
import org.Isa4.model.TradeAkzii;
import org.Isa4.producer.ProducerLogic;
import org.Isa4.repository.InformationAccountRepository;
import org.Isa4.repository.PositionInstrumentRepository;
import org.Isa4.repository.TradeAkziiRepository;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public final class KafkaService {

    private final KafkaAdmin kafkaAdmin;

    // Создание топиков заново перед началом работы
    public void cleanKafka() throws InterruptedException {
        log.info("KafkaService cleanKafka");
        for (Topics topics : Topics.values()) {
            //    kafkaAdmin.createOrModifyTopics(new NewTopic(topics.getName(), 1, (short) 1));  Альтернативная команда
            kafkaAdmin.createOrModifyTopics(
                    TopicBuilder.name(topics.getName())
                            .partitions(1)
                            .replicas(1)
                            .config(TopicConfig.RETENTION_MS_CONFIG, "30000")
                            .build()
            );
        }
    }
}
