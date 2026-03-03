package com.udb.desafio2.dse.application.request.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceRequestCreate {

    @NotNull(message = "Service is required")
    private Long serviceId;

    @Size(max = 300, message = "Notes must not exceed 300 characters")
    private String notes;
}

