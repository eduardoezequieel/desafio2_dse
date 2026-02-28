package com.udb.desafio2.dse.infrastructure.persistence.adapter;

import com.udb.desafio2.dse.domain.user.model.User;
import com.udb.desafio2.dse.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindById() {
        User user = new User();
        user.updateData("Ana", "ana@example.com");

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());

        Optional<User> found = userRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Ana", found.get().getName());
        assertEquals("ana@example.com", found.get().getEmail());
    }
}

