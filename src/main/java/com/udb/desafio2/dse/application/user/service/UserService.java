package com.udb.desafio2.dse.application.user.service;

import com.udb.desafio2.dse.application.user.dto.UserRequest;
import com.udb.desafio2.dse.application.user.dto.UserResponse;
import com.udb.desafio2.dse.domain.user.model.Role;
import com.udb.desafio2.dse.domain.user.model.User;
import com.udb.desafio2.dse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        // Validar que el email no exista
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        User user = new User();
        user.updateData(request.getNombre(), request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? Role.fromValue(request.getRole()) : Role.ADMIN);
        user.setCreatedAt(LocalDateTime.now());

        return toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        user.updateData(request.getNombre(), request.getEmail());

        // Solo actualizar password si se proporciona
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Actualizar rol si se proporciona
        if (request.getRole() != null && !request.getRole().isBlank()) {
            user.setRole(Role.fromValue(request.getRole()));
        }

        return toResponse(userRepository.save(user));
    }

    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nombre(user.getName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().getValue() : "ADMIN")
                .createdAt(user.getCreatedAt())
                .build();
    }
}

