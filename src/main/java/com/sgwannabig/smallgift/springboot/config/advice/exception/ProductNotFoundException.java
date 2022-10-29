package com.sgwannabig.smallgift.springboot.config.advice.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(Long id) {
    super("id:" + id + "에 해당하는 상품이 존재하지 않습니다");
  }
}
