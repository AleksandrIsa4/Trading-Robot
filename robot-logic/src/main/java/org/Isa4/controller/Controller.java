package org.Isa4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("msg")
//@RequiredArgsConstructor
public class Controller {

    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

/*    @PostMapping
    public void sendOrder(Long msgId, String msg){
        kafkaTemplate.send("msg", msgId, msg);
    }*/

    @PostMapping
    public void sendMsg(Long msgId, String msg){
        ListenableFuture<SendResult<Long, String>> future = kafkaTemplate.send("msg", msgId, msg);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
