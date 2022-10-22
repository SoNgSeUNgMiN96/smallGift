package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.Product;
import lombok.Getter;

@Getter
public class RegistProductCommand {
  private Long shopId;
  private Product product;

  public RegistProductCommand(Long shopId, Product product) {
    this.shopId = shopId;
    this.product = product;
  }
}

