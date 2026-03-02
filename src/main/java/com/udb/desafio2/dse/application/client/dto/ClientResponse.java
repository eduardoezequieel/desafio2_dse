package com.udb.desafio2.dse.application.client.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClientResponse {
    private Long id;
    private String nombre;
    private String email;
    private String empresa;
    private String telefono;
    private LocalDateTime createdAt;
}
