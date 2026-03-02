package com.udb.desafio2.dse.application.auth.service;

import com.udb.desafio2.dse.application.auth.dto.LoginRequest;
import com.udb.desafio2.dse.application.auth.dto.LoginResponse;
import com.udb.desafio2.dse.application.auth.dto.RegisterRequest;
import com.udb.desafio2.dse.domain.client.model.Client;
import com.udb.desafio2.dse.domain.client.repository.ClientRepository;
import com.udb.desafio2.dse.domain.user.model.User;
import com.udb.desafio2.dse.domain.user.repository.UserRepository;
import com.udb.desafio2.dse.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        Client client = new Client();
        client.updateData(request.getNombre(), request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setCreatedAt(LocalDateTime.now());

        Client saved = clientRepository.save(client);

        String token = jwtTokenProvider.generateToken(saved.getEmail(), "CLIENT");

        return LoginResponse.builder()
                .id(saved.getId())
                .nombre(saved.getNombre())
                .email(saved.getEmail())
                .role("CLIENT")
                .token(token)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        // Try admin users first
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Correo electrónico o contraseña incorrectos");
            }
            String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().getValue());
            return LoginResponse.builder()
                    .id(user.getId())
                    .nombre(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole().getValue())
                    .token(token)
                    .build();
        }

        // Try clients
        Client client = clientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Correo electrónico o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), client.getPassword())) {
            throw new IllegalArgumentException("Correo electrónico o contraseña incorrectos");
        }

        String token = jwtTokenProvider.generateToken(client.getEmail(), "CLIENT");

        return LoginResponse.builder()
                .id(client.getId())
                .nombre(client.getNombre())
                .email(client.getEmail())
                .role("CLIENT")
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
