package com.project.crud.security.enums;

public enum AuthConstants {

    AUTH_HEADER("Authorization"),
    TOKEN_TYPE("BEARER");

    private final String type;

    AuthConstants(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
