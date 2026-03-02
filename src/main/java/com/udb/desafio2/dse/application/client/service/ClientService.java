package com.udb.desafio2.dse.application.client.service;

import com.udb.desafio2.dse.application.client.dto.ClientRequest;
import com.udb.desafio2.dse.application.client.dto.ClientResponse;
import com.udb.desafio2.dse.domain.client.model.Client;
import com.udb.desafio2.dse.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientResponse create(ClientRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }

        Client client = new Client();
        client.updateData(request.getNombre(), request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setEmpresa(request.getEmpresa());
        client.setTelefono(request.getTelefono());
        client.setCreatedAt(LocalDateTime.now());

        return toResponse(clientRepository.save(client));
    }

    public ClientResponse update(Long id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        client.updateData(request.getNombre(), request.getEmail());
        client.setEmpresa(request.getEmpresa());
        client.setTelefono(request.getTelefono());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            client.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return toResponse(clientRepository.save(client));
    }

    public ClientResponse getById(Long id) {
        return clientRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    public List<ClientResponse> getAll() {
        return clientRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        clientRepository.deleteById(id);
    }

    private ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .nombre(client.getNombre())
                .email(client.getEmail())
                .empresa(client.getEmpresa())
                .telefono(client.getTelefono())
                .createdAt(client.getCreatedAt())
                .build();
    }
}
