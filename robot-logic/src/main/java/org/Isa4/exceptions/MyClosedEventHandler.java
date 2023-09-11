package org.Isa4.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.enumeration.Topics;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class MyClosedEventHandler {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @EventListener
    public void handleContextClose(ContextClosedEvent event) {
        log.info("MyClosedEventHandler handleContextClose");
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        AdminClient adminClient = AdminClient.create(properties);
        adminClient.deleteTopics(Topics.fromAll());
        adminClient.close();
    }
}

