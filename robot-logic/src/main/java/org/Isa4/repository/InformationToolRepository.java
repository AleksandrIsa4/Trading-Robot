package org.Isa4.repository;

import org.Isa4.model.InformationTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InformationToolRepository extends JpaRepository<InformationTool, Long> {

    InformationTool findBySecCodeAndAndCreatedTimeIsAfter(String secCode, LocalDateTime CreatedTime);
}
