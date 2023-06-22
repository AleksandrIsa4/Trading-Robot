package org.Isa4;

import lombok.RequiredArgsConstructor;
import org.Isa4.model.Transaction;
import org.Isa4.service.LogicService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@SpringBootApplication
@RequiredArgsConstructor
public class MainService implements CommandLineRunner {

    private final LogicService logicService;
    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }

    @Override
    public void run(String... args){
        logicService.run();
    }

    @KafkaListener(topics="msg")
    public void orderListener(ConsumerRecord<Long, Transaction> record){
        System.out.println(record.partition());
        System.out.println(record.key());
        System.out.println(record.value());
    }
}