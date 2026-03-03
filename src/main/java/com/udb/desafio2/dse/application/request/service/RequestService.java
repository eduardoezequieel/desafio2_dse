package com.udb.desafio2.dse.application.request.service;

import com.udb.desafio2.dse.application.request.dto.ServiceRequestCreate;
import com.udb.desafio2.dse.application.request.dto.ServiceRequestResponse;
import com.udb.desafio2.dse.application.request.dto.StatusHistoryResponse;
import com.udb.desafio2.dse.application.request.dto.StatusUpdateRequest;
import com.udb.desafio2.dse.application.service.dto.ServiceResponse;
import com.udb.desafio2.dse.application.service.service.CatalogService;
import com.udb.desafio2.dse.domain.client.model.Client;
import com.udb.desafio2.dse.domain.client.repository.ClientRepository;
import com.udb.desafio2.dse.domain.request.model.RequestStatus;
import com.udb.desafio2.dse.domain.request.model.ServiceRequest;
import com.udb.desafio2.dse.domain.request.model.StatusHistory;
import com.udb.desafio2.dse.domain.request.repository.ServiceRequestRepository;
import com.udb.desafio2.dse.domain.request.repository.StatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestService {

    private final ServiceRequestRepository requestRepository;
    private final StatusHistoryRepository  historyRepository;
    private final ClientRepository         clientRepository;
    private final CatalogService           catalogService;

    public ServiceRequestResponse create(String clientEmail, ServiceRequestCreate dto) {
        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        ServiceResponse service = catalogService.getById(dto.getServiceId());
        if (!service.getActive()) {
            throw new IllegalArgumentException("Service is not available");
        }

        ServiceRequest req = new ServiceRequest();
        req.setClientId(client.getId());
        req.setServiceId(service.getId());
        req.setStatus(RequestStatus.PENDING);
        req.setNotes(dto.getNotes());
        req.setCreatedAt(LocalDateTime.now());
        req.setUpdatedAt(LocalDateTime.now());

        ServiceRequest saved = requestRepository.save(req);
        historyRepository.save(StatusHistory.builder()
                .requestId(saved.getId())
                .status(RequestStatus.PENDING)
                .comment("Solicitud creada")
                .changedAt(LocalDateTime.now())
                .build());

        return toResponse(saved, client, service);
    }

    public ServiceRequestResponse getById(Long id) {
        ServiceRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        Client client = clientRepository.findById(req.getClientId()).orElse(null);
        ServiceResponse service = tryGetService(req.getServiceId());
        List<StatusHistoryResponse> history = historyRepository.findByRequestId(id)
                .stream().map(this::toHistoryResponse).toList();
        ServiceRequestResponse response = toResponse(req, client, service);
        response.setHistory(history);
        return response;
    }

    public List<ServiceRequestResponse> getAll(RequestStatus status) {
        List<ServiceRequest> list = status != null
                ? requestRepository.findByStatus(status)
                : requestRepository.findAll();
        return list.stream().map(r -> {
            Client c = clientRepository.findById(r.getClientId()).orElse(null);
            ServiceResponse s = tryGetService(r.getServiceId());
            return toResponse(r, c, s);
        }).toList();
    }

    public List<ServiceRequestResponse> getByClient(String clientEmail) {
        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        return requestRepository.findByClientId(client.getId()).stream().map(r -> {
            ServiceResponse s = tryGetService(r.getServiceId());
            return toResponse(r, client, s);
        }).toList();
    }

    public ServiceRequestResponse updateStatus(Long id, StatusUpdateRequest dto) {
        ServiceRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        req.updateStatus(dto.getStatus());
        ServiceRequest saved = requestRepository.save(req);

        historyRepository.save(StatusHistory.builder()
                .requestId(id)
                .status(dto.getStatus())
                .comment(dto.getComment())
                .changedAt(LocalDateTime.now())
                .build());

        Client client = clientRepository.findById(saved.getClientId()).orElse(null);
        ServiceResponse service = tryGetService(saved.getServiceId());
        List<StatusHistoryResponse> history = historyRepository.findByRequestId(id)
                .stream().map(this::toHistoryResponse).toList();
        ServiceRequestResponse response = toResponse(saved, client, service);
        response.setHistory(history);
        return response;
    }

    public void cancel(Long id, String clientEmail) {
        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        ServiceRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        if (!req.getClientId().equals(client.getId())) {
            throw new IllegalArgumentException("Not authorized");
        }
        req.cancel();
        requestRepository.save(req);
        historyRepository.save(StatusHistory.builder()
                .requestId(id)
                .status(RequestStatus.CANCELLED)
                .comment("Cancelado por el cliente")
                .changedAt(LocalDateTime.now())
                .build());
    }

    public void delete(Long id) {
        if (!requestRepository.existsById(id)) {
            throw new IllegalArgumentException("Request not found");
        }
        requestRepository.deleteById(id);
    }

    public long countPending() {
        return requestRepository.countByStatus(RequestStatus.PENDING);
    }

    private ServiceResponse tryGetService(Long serviceId) {
        try { return catalogService.getById(serviceId); }
        catch (Exception e) { return null; }
    }

    private ServiceRequestResponse toResponse(ServiceRequest r, Client c, ServiceResponse s) {
        return ServiceRequestResponse.builder()
                .id(r.getId())
                .clientId(r.getClientId())
                .clientName(c != null ? c.getNombre() : "—")
                .clientEmail(c != null ? c.getEmail() : "—")
                .serviceId(r.getServiceId())
                .serviceName(s != null ? s.getName() : "—")
                .servicePrice(s != null ? "$" + s.getPrice().toPlainString() : "—")
                .serviceEstimatedTime(s != null ? s.getEstimatedTime() : "—")
                .status(r.getStatus().name())
                .statusLabel(r.getStatus().getLabel())
                .statusColor(r.getStatus().getColor())
                .statusType(r.getStatus().getType())
                .notes(r.getNotes())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }

    private StatusHistoryResponse toHistoryResponse(StatusHistory h) {
        return StatusHistoryResponse.builder()
                .status(h.getStatus().name())
                .label(h.getStatus().getLabel())
                .color(h.getStatus().getColor())
                .type(h.getStatus().getType())
                .comment(h.getComment())
                .changedAt(h.getChangedAt())
                .build();
    }
}

