package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateProductCommand {

  private Long productId;
  private Product product;

  private MultipartFile productImage;

  public UpdateProductCommand(Long productId, Product product, MultipartFile productImage) {
    this.productId = productId;
    this.product = product;
    this.productImage = productImage;
  }
}
