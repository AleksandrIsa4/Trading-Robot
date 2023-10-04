package com.enfernuz.quik.lua.rpc.application;

import com.enfernuz.quik.lua.rpc.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.enumeration.Topics;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.Properties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@EnableKafka
@Slf4j
@ComponentScan(basePackages = "com.enfernuz.quik.lua.rpc")
public class RpcExampleApplication implements CommandLineRunner {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    private final KafkaAdmin kafkaAdmin;

    public static void main(final String[] args) {
        SpringApplication.run(RpcExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        AdminClient adminClient = AdminClient.create(properties);
        log.info("RpcExampleApplication handleContextClose TopicsKafka.fromAll={} ", Topics.fromAll());
        adminClient.deleteTopics(Topics.fromAll());
        adminClient.close();
        log.info("RpcExampleApplication createTopics");
        for (Topics topics : Topics.values()) {
             kafkaAdmin.createOrModifyTopics(
                    TopicBuilder.name(topics.getName())
                            .partitions(1)
                            .replicas(1)
                            .config(TopicConfig.RETENTION_MS_CONFIG, "30000")
                            .build()
            );
        }
        LocalDateTime ldt = LocalDateTime.now();
        while (!KafkaService.logicRun.get() || KafkaService.logicRun == null) {
            System.out.println("Logic модуль не подключен");
            if (LocalDateTime.now().isAfter(ldt.plusSeconds(30))) {
                System.out.println("Logic модуль не подключился");
                System.exit(-1);
            }
            Thread.sleep(1000);
        }
    }
}
