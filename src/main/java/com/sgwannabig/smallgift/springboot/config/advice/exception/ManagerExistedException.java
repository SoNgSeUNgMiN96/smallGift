package com.sgwannabig.smallgift.springboot.config.advice.exception;

public class ManagerExistedException extends RuntimeException{

  public ManagerExistedException() {
  }

  public ManagerExistedException(String message) {
    super(message);
  }

  public ManagerExistedException(String message, Throwable cause) {
    super(message, cause);
  }
}
