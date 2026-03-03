package com.udb.desafio2.dse.infrastructure.web;

import com.udb.desafio2.dse.application.request.dto.ServiceRequestCreate;
import com.udb.desafio2.dse.application.request.dto.ServiceRequestResponse;
import com.udb.desafio2.dse.application.request.dto.StatusUpdateRequest;
import com.udb.desafio2.dse.application.request.service.RequestService;
import com.udb.desafio2.dse.domain.request.model.RequestStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CLIENT')")
    public ServiceRequestResponse create(@Valid @RequestBody ServiceRequestCreate dto, Authentication auth) {
        return requestService.create(auth.getName(), dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICAL_STAFF')")
    public List<ServiceRequestResponse> getAll(@RequestParam(required = false) RequestStatus status) {
        return requestService.getAll(status);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CLIENT')")
    public List<ServiceRequestResponse> getMy(Authentication auth) {
        return requestService.getByClient(auth.getName());
    }

    @GetMapping("/pending-count")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICAL_STAFF')")
    public Map<String, Long> getPendingCount() {
        return Map.of("count", requestService.countPending());
    }

    @GetMapping("/{id}")
    public ServiceRequestResponse getById(@PathVariable Long id) {
        return requestService.getById(id);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICAL_STAFF')")
    public ServiceRequestResponse updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest dto) {
        return requestService.updateStatus(id, dto);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CLIENT')")
    public void cancel(@PathVariable Long id, Authentication auth) {
        requestService.cancel(id, auth.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICAL_STAFF')")
    public void delete(@PathVariable Long id) {
        requestService.delete(id);
    }
}

