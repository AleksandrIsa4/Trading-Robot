package org.Isa4.repository;

import org.Isa4.model.BaseInfoToolKey;
import org.Isa4.model.TradeAkzii;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeAkziiRepository extends JpaRepository<TradeAkzii, BaseInfoToolKey> {

    boolean existsAllBy();
}
