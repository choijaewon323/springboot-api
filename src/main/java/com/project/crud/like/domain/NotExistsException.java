package com.project.crud.like.domain;

public class NotExistsException extends RuntimeException {
    public NotExistsException() {
    }

    public NotExistsException(String message) {
        super(message);
    }
}
