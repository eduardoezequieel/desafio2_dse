package com.udb.desafio2.dse.infrastructure.persistence.repository;

import com.udb.desafio2.dse.infrastructure.persistence.entity.ServiceRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestJpaRepository extends JpaRepository<ServiceRequestEntity, Long> {
    List<ServiceRequestEntity> findByClientId(Long clientId);
    List<ServiceRequestEntity> findByStatus(String status);
    long countByStatus(String status);
}

