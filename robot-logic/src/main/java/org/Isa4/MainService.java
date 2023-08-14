package org.Isa4;

import lombok.RequiredArgsConstructor;
import org.Isa4.service.InstrumentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class MainService implements CommandLineRunner {

    private final InstrumentService instrumentService;

    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }

    @Override
    public void run(String... args) {
        // instrumentService.info();
    }
}