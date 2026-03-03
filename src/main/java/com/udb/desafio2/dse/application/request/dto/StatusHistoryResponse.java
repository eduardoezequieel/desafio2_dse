package com.udb.desafio2.dse.application.request.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StatusHistoryResponse {
    private String status;
    private String label;
    private String color;
    private String type;
    private String comment;
    private LocalDateTime changedAt;
}

