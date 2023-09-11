package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Isa4.model.TransactionTrade;
import org.Isa4.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionTrade save(TransactionTrade transaction) {
        log.info("TransactionService save  transaction {}", transaction);
        TransactionTrade t = transactionRepository.save(transaction);
        return t;
    }
}
