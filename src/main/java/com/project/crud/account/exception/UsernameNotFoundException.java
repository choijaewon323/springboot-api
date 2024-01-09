package com.project.crud.account.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
