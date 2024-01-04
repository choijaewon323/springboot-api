package com.project.crud.security.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(username + " NotFoundException");
    }
}
