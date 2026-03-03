package com.udb.desafio2.dse.application.service.service;

import com.udb.desafio2.dse.application.service.dto.ServiceRequest;
import com.udb.desafio2.dse.application.service.dto.ServiceResponse;
import com.udb.desafio2.dse.domain.service.model.Service;
import com.udb.desafio2.dse.domain.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CatalogService {

    private final ServiceRepository serviceRepository;

    public ServiceResponse create(ServiceRequest request) {
        Service service = new Service();
        service.updateData(request.getName(), request.getDescription(), request.getPrice(), request.getEstimatedTime());
        service.setActive(true);
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());
        return toResponse(serviceRepository.save(service));
    }

    public ServiceResponse update(Long id, ServiceRequest request) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        service.updateData(request.getName(), request.getDescription(), request.getPrice(), request.getEstimatedTime());
        return toResponse(serviceRepository.save(service));
    }

    public ServiceResponse getById(Long id) {
        return serviceRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
    }

    public List<ServiceResponse> getAll(Boolean active, String name) {
        return serviceRepository.findAllByActiveAndNameContaining(active, name)
                .stream().map(this::toResponse).toList();
    }

    public ServiceResponse toggleActive(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        service.toggleActive();
        return toResponse(serviceRepository.save(service));
    }

    public void softDelete(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        if (!service.getActive()) return;
        service.toggleActive();
        serviceRepository.save(service);
    }

    public void hardDelete(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new IllegalArgumentException("Service not found");
        }
        serviceRepository.deleteById(id);
    }

    private ServiceResponse toResponse(Service service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .estimatedTime(service.getEstimatedTime())
                .active(service.getActive())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }
}

