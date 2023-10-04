package com.enfernuz.quik.lua.rpc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: Не используется
@RestController
@Slf4j
@RequiredArgsConstructor
@EnableKafka
public class KafkaController {

    @Async
    @GetMapping(value = "/runLogic")
    public boolean getrunLogic() {
        log.info("KafkaController getrunLogic");
        return true;
    }
}
