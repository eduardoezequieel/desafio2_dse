package com.udb.desafio2.dse.domain.request.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private Long id;
    private Long clientId;
    private Long serviceId;
    private RequestStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateStatus(RequestStatus newStatus) {
        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalArgumentException(
                "Transición inválida de " + this.status.getLabel() + " a " + newStatus.getLabel());
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        updateStatus(RequestStatus.CANCELLED);
    }
}

