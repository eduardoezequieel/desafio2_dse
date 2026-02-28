package com.udb.desafio2.dse.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        String acceptHeader = request.getHeader("Accept");
        boolean isBrowserRequest = acceptHeader != null && acceptHeader.contains("text/html");

        if (isBrowserRequest) {
            response.sendRedirect("/403");
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            objectMapper.findAndRegisterModules();
            objectMapper.writeValue(response.getWriter(), Map.of(
                    "status", 403,
                    "message", "Acceso denegado: no tienes permisos para acceder a este recurso",
                    "timestamp", LocalDateTime.now().toString()
            ));
        }
    }
}

