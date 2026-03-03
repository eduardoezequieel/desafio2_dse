package com.udb.desafio2.dse.infrastructure.persistence.adapter;

import com.udb.desafio2.dse.domain.service.model.Service;
import com.udb.desafio2.dse.domain.service.repository.ServiceRepository;
import com.udb.desafio2.dse.infrastructure.persistence.entity.ServiceEntity;
import com.udb.desafio2.dse.infrastructure.persistence.repository.ServiceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository jpaRepository;

    @Override
    public Service save(Service service) {
        return toDomain(jpaRepository.save(toEntity(service)));
    }

    @Override
    public Optional<Service> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Service> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Service> findAllByActiveAndNameContaining(Boolean active, String name) {
        if (active != null && name != null && !name.isBlank()) {
            return jpaRepository.findAllByActiveAndNameContainingIgnoreCase(active, name)
                    .stream().map(this::toDomain).toList();
        } else if (active != null) {
            return jpaRepository.findAllByActive(active)
                    .stream().map(this::toDomain).toList();
        } else if (name != null && !name.isBlank()) {
            return jpaRepository.findAllByNameContainingIgnoreCase(name)
                    .stream().map(this::toDomain).toList();
        }
        return findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private ServiceEntity toEntity(Service service) {
        ServiceEntity entity = new ServiceEntity();
        entity.setId(service.getId());
        entity.setName(service.getName());
        entity.setDescription(service.getDescription());
        entity.setPrice(service.getPrice());
        entity.setEstimatedTime(service.getEstimatedTime());
        entity.setActive(service.getActive() != null ? service.getActive() : true);
        entity.setCreatedAt(service.getCreatedAt());
        entity.setUpdatedAt(service.getUpdatedAt());
        return entity;
    }

    private Service toDomain(ServiceEntity entity) {
        return Service.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .estimatedTime(entity.getEstimatedTime())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

