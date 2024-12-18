package com.project.crud.common;

import com.project.crud.exception.CustomException;
import com.project.crud.exception.ErrorCode;
import com.project.crud.exception.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackages = "com.project.crud")
public class ExceptionApiController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorResponseDto.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        "internal server error",
                        LocalDateTime.now()));
    }
}
