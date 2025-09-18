package com.cedric_guette.portfolio.exceptions;

public class AdminAlreadyExistException extends RuntimeException {
    public AdminAlreadyExistException(String message) {
        super(message);
    }
}
