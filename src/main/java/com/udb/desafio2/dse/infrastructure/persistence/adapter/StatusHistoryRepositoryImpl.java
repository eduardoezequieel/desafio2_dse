package com.udb.desafio2.dse.infrastructure.persistence.adapter;

import com.udb.desafio2.dse.domain.request.model.RequestStatus;
import com.udb.desafio2.dse.domain.request.model.StatusHistory;
import com.udb.desafio2.dse.domain.request.repository.StatusHistoryRepository;
import com.udb.desafio2.dse.infrastructure.persistence.entity.StatusHistoryEntity;
import com.udb.desafio2.dse.infrastructure.persistence.repository.StatusHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatusHistoryRepositoryImpl implements StatusHistoryRepository {

    private final StatusHistoryJpaRepository jpa;

    @Override
    public StatusHistory save(StatusHistory h) {
        return toDomain(jpa.save(toEntity(h)));
    }

    @Override
    public List<StatusHistory> findByRequestId(Long requestId) {
        return jpa.findByRequestIdOrderByChangedAtAsc(requestId).stream().map(this::toDomain).toList();
    }

    private StatusHistoryEntity toEntity(StatusHistory h) {
        StatusHistoryEntity e = new StatusHistoryEntity();
        e.setId(h.getId());
        e.setRequestId(h.getRequestId());
        e.setStatus(h.getStatus().name());
        e.setComment(h.getComment());
        e.setChangedAt(h.getChangedAt());
        return e;
    }

    private StatusHistory toDomain(StatusHistoryEntity e) {
        return StatusHistory.builder()
                .id(e.getId())
                .requestId(e.getRequestId())
                .status(RequestStatus.valueOf(e.getStatus()))
                .comment(e.getComment())
                .changedAt(e.getChangedAt())
                .build();
    }
}

