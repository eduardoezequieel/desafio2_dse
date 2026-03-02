package com.udb.desafio2.dse.domain.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String empresa;
    private String telefono;
    private LocalDateTime createdAt;

    public void updateData(String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre es requerido");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Correo electrónico es requerido");
        }
        this.nombre = nombre.trim();
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
}
