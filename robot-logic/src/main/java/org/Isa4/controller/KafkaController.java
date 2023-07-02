package org.Isa4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.model.ParamExAll;
import org.Isa4.producer.ProducerLogic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("msg")
@RequiredArgsConstructor
public class KafkaController {

    private final ProducerLogic producerLogic;

    @PostMapping
    public void sendParam(@RequestBody ParamExAll dto) {
        producerLogic.sendParam(dto);
    }
}
