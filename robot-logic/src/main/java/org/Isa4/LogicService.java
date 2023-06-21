package org.Isa4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@SpringBootApplication
public class LogicService {
    public static void main(String[] args) {
        SpringApplication.run(LogicService.class, args);
    }

    @KafkaListener(topics="msg")
    public void msgListener(String msg){
        System.out.println(msg);
    }
}