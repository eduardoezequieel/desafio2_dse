package com.udb.desafio2.dse.infrastructure.persistence.repository;

import com.udb.desafio2.dse.infrastructure.persistence.entity.StatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryJpaRepository extends JpaRepository<StatusHistoryEntity, Long> {
    List<StatusHistoryEntity> findByRequestIdOrderByChangedAtAsc(Long requestId);
}

