package com.enfernuz.quik.lua.rpc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerAutomation {

    private final Logger logger = LoggerFactory.getLogger(KafkaListenerAutomation.class);

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public boolean startListener(String listenerId) {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(listenerId);
        assert listenerContainer != null : false;
        listenerContainer.start();
        logger.info("{} Kafka Listener Started", listenerId);
        return true;
    }

    public boolean stopListener(String listenerId) {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(listenerId);
        logger.info("Kafka Listener listenerContainer {} ", listenerContainer);
        assert listenerContainer != null : false;
        listenerContainer.stop();
        logger.info("{} Kafka Listener Stopped.", listenerId);
        return true;
    }
}
