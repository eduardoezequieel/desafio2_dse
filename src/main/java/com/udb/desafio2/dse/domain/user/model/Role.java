package com.udb.desafio2.dse.domain.user.model;

public enum Role {
    ADMIN("ADMIN"),
    INTERNAL_USER("INTERNAL_USER"),
    TECHNICAL_STAFF("TECHNICAL_STAFF");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Rol inválido: " + value);
    }
}

