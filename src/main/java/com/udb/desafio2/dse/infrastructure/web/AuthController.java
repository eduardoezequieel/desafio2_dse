package com.udb.desafio2.dse.infrastructure.web;

import com.udb.desafio2.dse.application.auth.dto.LoginRequest;
import com.udb.desafio2.dse.application.auth.dto.LoginResponse;
import com.udb.desafio2.dse.application.auth.dto.RegisterRequest;
import com.udb.desafio2.dse.application.auth.service.AuthService;
import com.udb.desafio2.dse.infrastructure.web.exception.AuthenticationFailedException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        try {
            LoginResponse loginResponse = authService.register(request);
            addJwtCookie(response, loginResponse.getToken());
            return loginResponse;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            LoginResponse loginResponse = authService.login(request);
            addJwtCookie(response, loginResponse.getToken());
            return loginResponse;
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationFailedException(ex.getMessage());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void addJwtCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(24))
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}

