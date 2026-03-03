package com.udb.desafio2.dse.application.request.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ServiceRequestResponse {
    private Long id;
    private Long clientId;
    private String clientName;
    private String clientEmail;
    private Long serviceId;
    private String serviceName;
    private String servicePrice;
    private String serviceEstimatedTime;
    private String status;
    private String statusLabel;
    private String statusColor;
    private String statusType;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<StatusHistoryResponse> history;
}

