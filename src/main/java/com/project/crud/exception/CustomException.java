package com.project.crud.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomException extends RuntimeException {
    private final String errorCode;
    private final String message;
    private final LocalDateTime throwAt;

    public CustomException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode.getCode();
        this.message = message;
        this.throwAt = LocalDateTime.now();
    }
}
