package com.udb.desafio2.dse.application.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String nombre;
    private String email;
    private String role;
    @JsonIgnore
    private String token;
}

