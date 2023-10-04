package org.Isa4.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.RunStatus;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyClosedEventHandler {

    // Объясвление переменной RunStatus.logicRun false при закрытии приложения
    @EventListener
    public void handleContextClose(ContextClosedEvent event) {
        log.info("MyClosedEventHandler handleContextClose logicRun={} ", RunStatus.logicRun.get());
        RunStatus.logicRun.set(false);
    }
}

