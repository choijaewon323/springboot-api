package com.project.crud.exception;

public enum ErrorCode {
    ACCOUNT_NOT_FOUND("E001"),
    BOARD_NOT_FOUND("E002"),
    ACCOUNT_ALREADY_EXIST("E003"),
    INTERNAL_SERVER_ERROR("E004"),
    ACCOUNT_CREATION_ERROR("E005"),
    INVALID_INPUT("E006"),
    INVALID_BOARD_OPERATION("E007"),
    INVALID_PAGE_NUMBER("E008"),
    INVALID_LIKE_OPERATION("E009"),
    PASSWORD_NOT_MATCH("E010"),
    REPLY_CREATION_ERROR("E011"),
    REPLY_NOT_FOUND("E012")
    ;

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
