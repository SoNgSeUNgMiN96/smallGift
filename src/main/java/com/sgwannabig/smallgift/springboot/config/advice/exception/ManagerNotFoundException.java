package com.sgwannabig.smallgift.springboot.config.advice.exception;

public class ManagerNotFoundException extends RuntimeException{

  public ManagerNotFoundException() {
  }

  public ManagerNotFoundException(String message) {
    super(message);
  }

  public ManagerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
