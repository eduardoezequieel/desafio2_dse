package com.udb.desafio2.dse.application.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceRequest {

    @NotBlank(message = "Service name is required")
    @Size(max = 100, message = "Service name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Service description is required")
    @Size(max = 500, message = "Service description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Service price is required")
    @DecimalMin(value = "0.01", message = "Service price must be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "Estimated time is required")
    @Size(max = 50, message = "Estimated time must not exceed 50 characters")
    private String estimatedTime;
}

