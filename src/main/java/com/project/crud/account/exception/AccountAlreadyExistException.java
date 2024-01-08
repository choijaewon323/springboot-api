package com.project.crud.account.exception;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException() {
    }

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
