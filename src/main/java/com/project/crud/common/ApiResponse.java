package com.project.crud.common;

import org.springframework.http.ResponseEntity;

public class ApiResponse {
    private ApiResponse() {}

    public static ResponseEntity<Void> ok() {
        return ResponseEntity
                .ok()
                .build();
    }

    public static <T> ResponseEntity<T> okWithBody(T body) {
        return ResponseEntity
                .ok()
                .body(body);
    }
}
