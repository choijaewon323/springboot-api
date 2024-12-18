package com.project.crud.exception;

public enum ErrorCode {
    USER_NOT_FOUND("E001"),
    BOARD_NOT_FOUND("E002")
    ;

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
