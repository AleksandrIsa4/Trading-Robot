package com.enfernuz.quik.lua.rpc.service;

import com.enfernuz.quik.lua.rpc.api.structures.TransReply;
import com.enfernuz.quik.lua.rpc.dto.RunStatus;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.dto.TransactionDto;
import org.Isa4.dto.constant.ModulConstans;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ReplyQUIKservice {

    private static volatile Map<Long, TransReply> transReplyMap = new ConcurrentHashMap<>();

    private static long DELAY_TIME = ModulConstans.DELAY_TIME;

    // Добавление информации об ответе терминала Quik на транзакцию
    public static void addTransReply(TransReply transReply) {
        log.info("ReplyQUIKservice addTransReply  transReply {}", transReply);
        if (transReply.getStatus() != 3) {
            log.info("ReplyQUIKservice addTransReply  error !=3");
            System.exit(1);
        }
        transReplyMap.put(transReply.getTransId(), transReply);
        System.out.println("addTransReply   " + transReplyMap);
    }

    // Считывание информации об ответе терминала Quik на транзакцию
    @Async
    public TransactionDto getTransReply(TransactionDto transactionDto) {
        log.info("ReplyQUIKservice getTransReply  transactionDto {}", transactionDto);
        LocalDateTime ldt = LocalDateTime.now();
        Long transId = transactionDto.getTransId();
        try {
            while (LocalDateTime.now().isBefore(ldt.plusSeconds(DELAY_TIME)) && RunStatus.rpcRun.get()) {
                log.info("ReplyQUIKservice getTransReply  transReplyMap {}", transReplyMap);
                if (transReplyMap.containsKey(transId)) {
                    transactionDto.setOrderNumber(Long.parseLong(transReplyMap.get(transId).getOrderNum()));
                    transReplyMap.remove(transactionDto.getTransId());
                    System.out.println("getTransReply   " + transReplyMap);
                    return transactionDto;
                }
                Thread.sleep(500);
                System.out.println("ReplyQUIKservice getTransReply while RunStatus.rpcRun   " + RunStatus.rpcRun.get());
            }
        } catch (InterruptedException e) {
            System.exit(1);
        }
        throw new RuntimeException("Исключение в методе getTransReply в классе ReplyQUIKservice из-за задержки получения сообщения");
    }
}
