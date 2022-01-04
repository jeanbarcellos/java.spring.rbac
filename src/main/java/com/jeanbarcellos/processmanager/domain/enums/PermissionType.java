package com.jeanbarcellos.processmanager.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PermissionType {
    INHERITED("inherited"),
    OWN("own");

    public final String label;

    private PermissionType(String label) {
        this.label = label;
    }

    @JsonValue
    public String toValue() {
        return label;
    }
}
