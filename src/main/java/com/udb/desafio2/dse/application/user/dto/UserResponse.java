package com.udb.desafio2.dse.application.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String nombre;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}

