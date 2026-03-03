package com.udb.desafio2.dse.infrastructure.web.util;

public class RoleTranslator {

    public static String translateRole(String role) {
        if (role == null || role.isBlank()) {
            return "Usuario";
        }

        return switch (role.toUpperCase()) {
            case "ADMIN" -> "Administrador";
            case "INTERNAL_USER" -> "Usuario Interno";
            case "CLIENT" -> "Cliente";
            default -> "Usuario";
        };
    }
}

