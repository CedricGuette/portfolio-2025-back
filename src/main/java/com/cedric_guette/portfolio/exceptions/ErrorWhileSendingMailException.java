package com.cedric_guette.portfolio.exceptions;

public class ErrorWhileSendingMailException extends RuntimeException {
    public ErrorWhileSendingMailException(String message) {
        super(message);
    }
}
