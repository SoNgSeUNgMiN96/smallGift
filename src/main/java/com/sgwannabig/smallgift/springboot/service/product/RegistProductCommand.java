package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class RegistProductCommand {
  private Long managerId;
  private Product product;

  private MultipartFile productImage;

  public RegistProductCommand(Long managerId, Product product, MultipartFile productImage) {
    this.managerId = managerId;
    this.product = product;
    this.productImage = productImage;
  }
}

