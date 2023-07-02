package org.Isa4.service;

import lombok.RequiredArgsConstructor;
import org.Isa4.model.Transaction;
import org.Isa4.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        Transaction t = transactionRepository.save(transaction);
        return t;
    }
}
