package com.cedric_guette.portfolio.exceptions;

public class DatabaseAlreadyFilledException extends RuntimeException {
    public DatabaseAlreadyFilledException(String message) {
        super(message);
    }
}
