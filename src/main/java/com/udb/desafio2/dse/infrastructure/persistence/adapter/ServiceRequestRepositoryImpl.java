package com.udb.desafio2.dse.infrastructure.persistence.adapter;

import com.udb.desafio2.dse.domain.request.model.RequestStatus;
import com.udb.desafio2.dse.domain.request.model.ServiceRequest;
import com.udb.desafio2.dse.domain.request.repository.ServiceRequestRepository;
import com.udb.desafio2.dse.infrastructure.persistence.entity.ServiceRequestEntity;
import com.udb.desafio2.dse.infrastructure.persistence.repository.ServiceRequestJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ServiceRequestRepositoryImpl implements ServiceRequestRepository {

    private final ServiceRequestJpaRepository jpa;

    @Override
    public ServiceRequest save(ServiceRequest r) {
        return toDomain(jpa.save(toEntity(r)));
    }

    @Override
    public Optional<ServiceRequest> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<ServiceRequest> findAll() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<ServiceRequest> findByClientId(Long clientId) {
        return jpa.findByClientId(clientId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<ServiceRequest> findByStatus(RequestStatus status) {
        return jpa.findByStatus(status.name()).stream().map(this::toDomain).toList();
    }

    @Override
    public long countByStatus(RequestStatus status) {
        return jpa.countByStatus(status.name());
    }

    @Override
    public boolean existsById(Long id) {
        return jpa.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    private ServiceRequestEntity toEntity(ServiceRequest r) {
        ServiceRequestEntity e = new ServiceRequestEntity();
        e.setId(r.getId());
        e.setClientId(r.getClientId());
        e.setServiceId(r.getServiceId());
        e.setStatus(r.getStatus().name());
        e.setNotes(r.getNotes());
        e.setCreatedAt(r.getCreatedAt());
        e.setUpdatedAt(r.getUpdatedAt());
        return e;
    }

    private ServiceRequest toDomain(ServiceRequestEntity e) {
        return ServiceRequest.builder()
                .id(e.getId())
                .clientId(e.getClientId())
                .serviceId(e.getServiceId())
                .status(RequestStatus.valueOf(e.getStatus()))
                .notes(e.getNotes())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}

