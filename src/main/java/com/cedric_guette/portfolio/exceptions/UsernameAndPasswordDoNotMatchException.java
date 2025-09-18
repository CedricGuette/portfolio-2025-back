package com.cedric_guette.portfolio.exceptions;

public class UsernameAndPasswordDoNotMatchException extends RuntimeException {
    public UsernameAndPasswordDoNotMatchException(String message) {
        super(message);
    }
}
