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
public class StatusHistory {
    private Long id;
    private Long requestId;
    private RequestStatus status;
    private String comment;
    private LocalDateTime changedAt;
}

