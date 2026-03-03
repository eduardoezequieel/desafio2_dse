package com.udb.desafio2.dse.infrastructure.web;

import com.udb.desafio2.dse.application.service.dto.ServiceRequest;
import com.udb.desafio2.dse.application.service.dto.ServiceResponse;
import com.udb.desafio2.dse.application.service.service.CatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceCatalogController {

    private final CatalogService catalogService;

    @GetMapping
    public List<ServiceResponse> getAll(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String name) {
        return catalogService.getAll(active, name);
    }

    @GetMapping("/{id}")
    public ServiceResponse getById(@PathVariable Long id) {
        return catalogService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse create(@Valid @RequestBody ServiceRequest request) {
        return catalogService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse update(@PathVariable Long id, @Valid @RequestBody ServiceRequest request) {
        return catalogService.update(id, request);
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceResponse toggleActive(@PathVariable Long id) {
        return catalogService.toggleActive(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void softDelete(@PathVariable Long id) {
        catalogService.softDelete(id);
    }

    @DeleteMapping("/{id}/permanent")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void hardDelete(@PathVariable Long id) {
        catalogService.hardDelete(id);
    }
}

