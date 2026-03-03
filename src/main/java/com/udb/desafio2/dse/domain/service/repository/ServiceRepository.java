package com.udb.desafio2.dse.domain.service.repository;

import com.udb.desafio2.dse.domain.service.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    Service save(Service service);
    Optional<Service> findById(Long id);
    List<Service> findAll();
    List<Service> findAllByActiveAndNameContaining(Boolean active, String name);
    boolean existsById(Long id);
    void deleteById(Long id);
}

