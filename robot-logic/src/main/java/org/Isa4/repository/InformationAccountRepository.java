package org.Isa4.repository;

import org.Isa4.model.InformationAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InformationAccountRepository extends JpaRepository<InformationAccount, String> {

    @Modifying
    @Query("UPDATE InformationAccount i SET i.money = ?1 WHERE i.firmId=?2 AND i.tagMoney=?3 AND i.clientCode=?4")
    void saveMoney(float money, String firmId, String tagMoney, String clientCode);

    boolean existsAllBy();

    Optional<InformationAccount> findFirstByOrderByAccountDesc();
}
