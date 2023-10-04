package com.enfernuz.quik.lua.rpc.exceptions;

import com.enfernuz.quik.lua.rpc.dto.RunStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MyClosedEventHandler {

    @EventListener
    public void handleContextStart(ContextClosedEvent event) {
        log.info("MyClosedEventHandler handleContextClose rpcRun={} ", RunStatus.rpcRun.get());
        RunStatus.rpcRun.set(false);
    }
}

