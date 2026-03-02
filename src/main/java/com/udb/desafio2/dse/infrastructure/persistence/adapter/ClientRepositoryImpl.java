package com.udb.desafio2.dse.infrastructure.persistence.adapter;

import com.udb.desafio2.dse.domain.client.model.Client;
import com.udb.desafio2.dse.domain.client.repository.ClientRepository;
import com.udb.desafio2.dse.infrastructure.persistence.entity.ClientEntity;
import com.udb.desafio2.dse.infrastructure.persistence.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository jpaRepository;

    @Override
    public Client save(Client client) {
        ClientEntity entity = toEntity(client);
        ClientEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private ClientEntity toEntity(Client client) {
        ClientEntity entity = new ClientEntity();
        entity.setId(client.getId());
        entity.setNombre(client.getNombre());
        entity.setEmail(client.getEmail());
        entity.setPassword(client.getPassword());
        entity.setEmpresa(client.getEmpresa());
        entity.setTelefono(client.getTelefono());
        entity.setCreatedAt(client.getCreatedAt());
        return entity;
    }

    private Client toDomain(ClientEntity entity) {
        return Client.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .empresa(entity.getEmpresa())
                .telefono(entity.getTelefono())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
