package com.udb.desafio2.dse.domain.request.model;

public enum RequestStatus {
    PENDING("Pendiente", "#f59e0b", "warning"),
    IN_REVIEW("En revisión", "#3b82f6", "info"),
    IN_PROGRESS("En progreso", "#8b5cf6", "purple"),
    COMPLETED("Completado", "#10b981", "success"),
    REJECTED("Rechazado", "#ef4444", "danger"),
    CANCELLED("Cancelado", "#6b7280", "neutral");

    private final String label;
    private final String color;
    private final String type;

    RequestStatus(String label, String color, String type) {
        this.label = label;
        this.color = color;
        this.type = type;
    }

    public String getLabel() { return label; }
    public String getColor() { return color; }
    public String getType()  { return type; }

    public boolean canTransitionTo(RequestStatus next) {
        return switch (this) {
            case PENDING     -> next == IN_REVIEW || next == REJECTED || next == CANCELLED;
            case IN_REVIEW   -> next == IN_PROGRESS || next == REJECTED;
            case IN_PROGRESS -> next == COMPLETED || next == REJECTED;
            case COMPLETED, REJECTED, CANCELLED -> false;
        };
    }
}

