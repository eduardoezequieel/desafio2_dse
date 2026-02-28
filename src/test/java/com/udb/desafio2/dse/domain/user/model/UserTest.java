package com.udb.desafio2.dse.domain.user.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void updateData_normalizaNombreYEmail() {
        User user = new User();

        user.updateData("  Juan Perez  ", "TEST@EXAMPLE.COM ");

        assertEquals("Juan Perez", user.getName());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void updateData_rechazaNombreVacio() {
        User user = new User();

        assertThrows(IllegalArgumentException.class, () -> user.updateData(" ", "a@b.com"));
    }

    @Test
    void updateData_rechazaEmailVacio() {
        User user = new User();

        assertThrows(IllegalArgumentException.class, () -> user.updateData("Ana", ""));
    }
}

