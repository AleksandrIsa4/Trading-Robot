package org.Isa4.repository;

import org.Isa4.model.InformationTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InformationToolRepository extends JpaRepository<InformationTool, Long> {

    Optional<InformationTool> findTop1BySecCodeAndAndCreatedTimeIsAfterOrderByCreatedTimeAsc(String secCode, LocalDateTime CreatedTime);
}
