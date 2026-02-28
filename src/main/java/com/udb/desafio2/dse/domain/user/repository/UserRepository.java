package com.udb.desafio2.dse.domain.user.repository;

import com.udb.desafio2.dse.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}

