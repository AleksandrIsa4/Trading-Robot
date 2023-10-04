package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.enumeration.Topics;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
@RequiredArgsConstructor
public final class KafkaService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    public static AtomicBoolean rpcRun = new AtomicBoolean(false);

    private final KafkaAdmin kafkaAdmin;

    // Создание топиков заново перед началом работы
    public void createTopics() throws InterruptedException {
        deleteTopics();
        log.info("KafkaService createTopics");
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

    public void deleteTopics() {
        log.info("KafkaService deleteTopics");
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        AdminClient adminClient = AdminClient.create(properties);
        log.info("KafkaService deleteTopics TopicsKafka.fromAll={} ", Topics.fromAll());
        adminClient.deleteTopics(Topics.fromAll());
        adminClient.close();
    }

    @Scheduled(initialDelay = 30000, fixedRate = 3000)
    private void stopApp() {
        if (rpcRun.get()) {
            log.info("KafkaService stopApp true");
            rpcRun.set(false);
        } else {
            log.info("KafkaService stopApp false exit");
            System.exit(1);
        }
    }
}
