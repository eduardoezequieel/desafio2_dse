package com.udb.desafio2.dse.domain.client.repository;

import com.udb.desafio2.dse.domain.client.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Long id);
    Optional<Client> findByEmail(String email);
    List<Client> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}
