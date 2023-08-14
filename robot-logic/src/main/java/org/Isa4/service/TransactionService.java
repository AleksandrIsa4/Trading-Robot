package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.model.TransactionTrade;
import org.Isa4.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionTrade save(TransactionTrade transaction) {
        TransactionTrade t = transactionRepository.save(transaction);
        return t;
    }
}
