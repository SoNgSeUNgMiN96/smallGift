package com.sgwannabig.smallgift.springboot.config.advice.exception;

public class LoginFailureException extends RuntimeException{
    public LoginFailureException() {
    }

    public LoginFailureException(String message) {
        super(message);
    }

    public LoginFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}