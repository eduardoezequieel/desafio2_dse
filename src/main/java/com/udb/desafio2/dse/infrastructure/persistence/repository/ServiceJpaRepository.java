package com.udb.desafio2.dse.infrastructure.persistence.repository;

import com.udb.desafio2.dse.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findAllByActiveAndNameContainingIgnoreCase(Boolean active, String name);
    List<ServiceEntity> findAllByNameContainingIgnoreCase(String name);
    List<ServiceEntity> findAllByActive(Boolean active);
}

