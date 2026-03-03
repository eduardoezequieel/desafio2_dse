package com.udb.desafio2.dse.domain.request.repository;

import com.udb.desafio2.dse.domain.request.model.StatusHistory;

import java.util.List;

public interface StatusHistoryRepository {
    StatusHistory save(StatusHistory history);
    List<StatusHistory> findByRequestId(Long requestId);
}

