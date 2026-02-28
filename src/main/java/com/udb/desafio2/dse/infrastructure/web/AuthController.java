package com.udb.desafio2.dse.infrastructure.web;

import com.udb.desafio2.dse.application.auth.dto.LoginRequest;
import com.udb.desafio2.dse.application.auth.dto.LoginResponse;
import com.udb.desafio2.dse.application.auth.dto.RegisterRequest;
import com.udb.desafio2.dse.application.auth.service.AuthService;
import com.udb.desafio2.dse.infrastructure.web.exception.AuthenticationFailedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse register(@Valid @RequestBody RegisterRequest request) {
        try {
            return authService.register(request);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        try {
            return authService.login(request);
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationFailedException(ex.getMessage());
        }
    }
}

