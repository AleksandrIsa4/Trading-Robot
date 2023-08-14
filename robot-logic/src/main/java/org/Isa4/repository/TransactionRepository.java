package org.Isa4.repository;

import org.Isa4.model.TransactionTrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionTrade, Long> {
}
