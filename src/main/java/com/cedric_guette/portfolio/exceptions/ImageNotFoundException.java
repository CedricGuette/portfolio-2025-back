package com.cedric_guette.portfolio.exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String message) {
        super(message);
    }
}
