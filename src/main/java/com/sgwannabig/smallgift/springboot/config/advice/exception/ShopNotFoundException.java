package com.sgwannabig.smallgift.springboot.config.advice.exception;

public class ShopNotFoundException extends RuntimeException{

  public ShopNotFoundException() {
  }

  public ShopNotFoundException(String message) {
    super(message);
  }
}
