package com.enfernuz.quik.lua.rpc.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
@ComponentScan(basePackages = "com.enfernuz.quik.lua.rpc")
public class RpcExampleApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(RpcExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {

    }
}
