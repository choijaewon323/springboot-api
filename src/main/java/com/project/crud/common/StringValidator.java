package com.project.crud.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringValidator {
    public static void checkNotBlankAndNotNull(String s, String errorMessage) {
        if (s == null) {
            throw new IllegalArgumentException(errorMessage);
        }

        if (s.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
