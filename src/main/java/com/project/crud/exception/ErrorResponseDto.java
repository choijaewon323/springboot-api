package com.project.crud.exception;

public record ErrorResponseDto(
        String code,
        String message,
        String when
) {
    public static ErrorResponseDto of(CustomException e) {
        return new ErrorResponseDto(e.getErrorCode(), e.getMessage(), e.getThrowAt().toString());
    }
}
