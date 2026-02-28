package com.udb.desafio2.dse.application.auth.service;

import com.udb.desafio2.dse.application.auth.dto.LoginRequest;
import com.udb.desafio2.dse.application.auth.dto.LoginResponse;
import com.udb.desafio2.dse.application.auth.dto.RegisterRequest;
import com.udb.desafio2.dse.domain.user.model.Role;
import com.udb.desafio2.dse.domain.user.model.User;
import com.udb.desafio2.dse.domain.user.repository.UserRepository;
import com.udb.desafio2.dse.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse register(RegisterRequest request) {
        // Validar que las contraseñas coincidan
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        // Validar que el email no exista
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        // Crear nuevo usuario
        User user = new User();
        user.updateData(request.getNombre(), request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // Generar token
        String token = jwtTokenProvider.generateToken(savedUser.getEmail(), savedUser.getRole().getValue());

        return LoginResponse.builder()
                .id(savedUser.getId())
                .nombre(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().getValue())
                .token(token)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        // Buscar usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Correo electrónico o contraseña incorrectos"));

        // Validar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Correo electrónico o contraseña incorrectos");
        }

        // Generar token
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().getValue());

        return LoginResponse.builder()
                .id(user.getId())
                .nombre(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .token(token)
                .build();
    }

    public User validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Token inválido o expirado");
        }

        String email = jwtTokenProvider.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}

