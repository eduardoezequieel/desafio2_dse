package com.udb.desafio2.dse.domain.request.repository;

import com.udb.desafio2.dse.domain.request.model.RequestStatus;
import com.udb.desafio2.dse.domain.request.model.ServiceRequest;

import java.util.List;
import java.util.Optional;

public interface ServiceRequestRepository {
    ServiceRequest save(ServiceRequest request);
    Optional<ServiceRequest> findById(Long id);
    List<ServiceRequest> findAll();
    List<ServiceRequest> findByClientId(Long clientId);
    List<ServiceRequest> findByStatus(RequestStatus status);
    long countByStatus(RequestStatus status);
    boolean existsById(Long id);
    void deleteById(Long id);
}

