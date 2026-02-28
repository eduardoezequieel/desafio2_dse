package com.udb.desafio2.dse.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String acceptHeader = request.getHeader("Accept");
        boolean isBrowserRequest = acceptHeader != null && acceptHeader.contains("text/html");

        if (isBrowserRequest) {
            String originalUrl = request.getRequestURI();
            String query = request.getQueryString();
            if (query != null) {
                originalUrl += "?" + query;
            }
            response.sendRedirect("/login?redirect=" + URLEncoder.encode(originalUrl, StandardCharsets.UTF_8));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            objectMapper.findAndRegisterModules();
            objectMapper.writeValue(response.getWriter(), Map.of(
                    "status", 401,
                    "message", "No autenticado: " + authException.getMessage(),
                    "timestamp", LocalDateTime.now().toString()
            ));
        }
    }
}



