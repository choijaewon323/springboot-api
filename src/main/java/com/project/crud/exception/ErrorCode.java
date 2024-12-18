package com.project.crud.exception;

public enum ErrorCode {
    ACCOUNT_NOT_FOUND("E001"),
    BOARD_NOT_FOUND("E002"),
    ACCOUNT_ALREADY_EXIST("E003"),
    INTERNAL_SERVER_ERROR("E004"),
    ACCOUNT_CREATION_ERROR("E005"),
    ;

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
