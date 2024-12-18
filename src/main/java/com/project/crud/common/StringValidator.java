package com.project.crud.common;

import com.project.crud.exception.CustomException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.crud.exception.ErrorCode.INVALID_INPUT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringValidator {
    public static void checkNotBlankAndNotNull(String s, String errorMessage) {
        if (s == null) {
            throw new CustomException(INVALID_INPUT, errorMessage);
        }

        if (s.isBlank()) {
            throw new CustomException(INVALID_INPUT, errorMessage);
        }
    }
}
