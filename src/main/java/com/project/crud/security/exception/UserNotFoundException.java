package com.project.crud.security.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String username) {
        super(username + " NotFoundException");
    }
}
