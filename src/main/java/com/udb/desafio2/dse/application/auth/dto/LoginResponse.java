package com.udb.desafio2.dse.application.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String nombre;
    private String email;
    private String role;
    private String token;
}

