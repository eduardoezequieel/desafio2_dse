package com.udb.desafio2.dse.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;

    public void updateData(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nombre es requerido");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Correo electrónico es requerido");
        }
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Contraseña es requerida");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        this.password = password;
    }

    public void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Correo electrónico es requerido");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
    }
}

