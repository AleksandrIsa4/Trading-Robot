package org.Isa4.repository;

import org.Isa4.model.BaseInfoToolKey;
import org.Isa4.model.PositionInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionInstrumentRepository extends JpaRepository<PositionInstrument, BaseInfoToolKey> {

    boolean existsAllBy();
}
