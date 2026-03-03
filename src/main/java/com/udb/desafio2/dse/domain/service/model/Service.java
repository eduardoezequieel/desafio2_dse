package com.udb.desafio2.dse.domain.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String estimatedTime; // e.g. "2 horas", "3 días"
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateData(String name, String description, BigDecimal price, String estimatedTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Service name is required");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Service name must not exceed 100 characters");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Service description is required");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("Service description must not exceed 500 characters");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Service price must be greater than zero");
        }
        if (estimatedTime == null || estimatedTime.isBlank()) {
            throw new IllegalArgumentException("Estimated time is required");
        }
        this.name = name.trim();
        this.description = description.trim();
        this.price = price;
        this.estimatedTime = estimatedTime.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void toggleActive() {
        this.active = !this.active;
        this.updatedAt = LocalDateTime.now();
    }
}

