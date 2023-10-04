package com.enfernuz.quik.lua.rpc.service;

import com.enfernuz.quik.lua.rpc.dto.RunStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class KafkaService {

    public static AtomicBoolean logicRun = new AtomicBoolean(false);

    // Остановка модуля в случае отлючения модуля robot-logic
    @Scheduled(initialDelay = 30000, fixedRate = 3000)
    private void stopApp() {
        if (logicRun.get()) {
            log.info("KafkaService stopApp true");
            logicRun.set(false);
        } else {
            log.info("KafkaService stopApp false exit");
            RunStatus.rpcRun.set(false);
            System.exit(1);
        }
    }
}
