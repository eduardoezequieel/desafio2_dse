package com.udb.desafio2.dse.application.request.dto;

import com.udb.desafio2.dse.domain.request.model.RequestStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private RequestStatus status;

    @Size(max = 300, message = "Comment must not exceed 300 characters")
    private String comment;
}

