package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.Product;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class RegistProductCommand {
  private Long shopId;
  private Product product;

  private MultipartFile productImage;

  public RegistProductCommand(Long shopId, Product product, MultipartFile productImage) {
    this.shopId = shopId;
    this.product = product;
    this.productImage = productImage;
  }
}

