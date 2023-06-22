package org.Isa4.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.model.Transaction;
import org.Isa4.service.TransactionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("msg")
@RequiredArgsConstructor
public class Controller {

    private final KafkaTemplate<Long, Transaction> kafkaTemplate;

    private final TransactionService transactionService;

    @PostMapping
        public Transaction sendMsg(@RequestBody Transaction dto){
        ListenableFuture<SendResult<Long, Transaction>> future = kafkaTemplate.send("msg", dto.getId(), dto);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
        return transactionService.save(dto);
    }
}
