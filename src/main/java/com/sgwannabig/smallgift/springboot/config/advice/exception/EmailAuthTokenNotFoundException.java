package com.sgwannabig.smallgift.springboot.config.advice.exception;

public class EmailAuthTokenNotFoundException extends RuntimeException{
    public EmailAuthTokenNotFoundException() {
    }

    public EmailAuthTokenNotFoundException(String message) {
        super(message);
    }

    public EmailAuthTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}