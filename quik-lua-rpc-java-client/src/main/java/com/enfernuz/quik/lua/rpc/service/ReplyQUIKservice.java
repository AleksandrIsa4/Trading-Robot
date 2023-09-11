package com.enfernuz.quik.lua.rpc.service;

import com.enfernuz.quik.lua.rpc.api.structures.TransReply;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.TransactionDto;
import org.Isa4.dto.enumeration.Status;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ReplyQUIKservice {

    private static volatile Map<Long, TransReply> transReplyMap = new ConcurrentHashMap<>();

    static long DELAY_TIME = 999L;

    public static void addTransReply(TransReply transReply) {
        log.info("ReplyQUIKservice addTransReply  transReply {}", transReply);
        if (transReply.getStatus() != 3) {
            System.exit(1);
        }
        transReplyMap.put(transReply.getTransId(), transReply);
        System.out.println("addTransReply   " + transReplyMap);
    }

    @Async
    public TransactionDto getTransReply(TransactionDto transactionDto) {
        log.info("ReplyQUIKservice getTransReply  transactionDto {}", transactionDto);
        LocalDateTime ldt = LocalDateTime.now();
        Long transId = transactionDto.getTransId();
        try {
            while (LocalDateTime.now().isBefore(ldt.plusSeconds(DELAY_TIME)) && !Thread.currentThread().isInterrupted()) {
                if(Thread.interrupted()){

                }
                if (transReplyMap.containsKey(transId)) {
                    transactionDto.setOrderNumber(Long.parseLong(transReplyMap.get(transId).getOrderNum()));
                    long quantityComplete = transactionDto.getQuantity() - (long) Float.parseFloat(transReplyMap.get(transId).getBalance());
                    transactionDto.setQuantityComplete(quantityComplete);
                    if (quantityComplete == transactionDto.getQuantity()) {
                        transactionDto.setStatus(Status.COMPLETED);
                    } else {
                        transactionDto.setStatus(Status.ACTIVE);
                    }
                    transReplyMap.remove(transactionDto.getTransId());
                    System.out.println("getTransReply   " + transReplyMap);
                    return transactionDto;
                }
                Thread.sleep(500);
                System.out.println("ReplyQUIKservice getTransReply while");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.exit(1);
        }
        throw new RuntimeException("Исключение в методе getTransReply в классе ReplyQUIKservice из-за задержки получения сообщения");
    }

    public void cancel() {
        System.out.println("ReplyQUIKservice cancel");
        Thread.currentThread().interrupt();
    }
}
