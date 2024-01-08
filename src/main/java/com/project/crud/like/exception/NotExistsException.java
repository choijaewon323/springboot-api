package com.project.crud.like.exception;

public class NotExistsException extends RuntimeException {
    public NotExistsException() {
    }

    public NotExistsException(String message) {
        super(message);
    }
}
