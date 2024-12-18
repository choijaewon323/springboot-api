package com.project.crud.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String code,
        String message,
        LocalDateTime throwAt
) {
    public static ErrorResponseDto of(CustomException e) {
        return new ErrorResponseDto(e.getErrorCode(), e.getMessage(), e.getThrowAt());
    }
}
