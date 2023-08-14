package org.Isa4.service;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class KafkaService {

    @Value("${kafka.delay.second}")
    private long KAFKA_DELAY_SECOND;

    // TODO: Не доделана
    // Удаление очереди в топике
    public void timeKafka(String[] args) {
        try {
            //здесь "sleep 15" и есть ваша консольная команда
            Process proc = Runtime.getRuntime().exec("kafka-configs.sh --zookeeper localhost:2181 --alter --entity-type topics --add-config retention.ms=1000 --entity-name text_topic");
            proc.waitFor();
            proc.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
