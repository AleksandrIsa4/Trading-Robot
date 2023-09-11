package com.enfernuz.quik.lua.rpc.exceptions;

import com.enfernuz.quik.lua.rpc.service.ReplyQUIKservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyClosedEventHandler {

    private final ReplyQUIKservice replyQUIKservice;

    @EventListener
    public void handleContextStart(ContextClosedEvent event) {
        System.out.println("MyClosedEventHandler handleContextStart");
        replyQUIKservice.cancel();
    }
}

