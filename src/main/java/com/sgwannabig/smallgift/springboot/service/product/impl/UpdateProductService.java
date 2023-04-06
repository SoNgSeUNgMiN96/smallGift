package com.sgwannabig.smallgift.springboot.service.product.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ProductNotFoundException;
import com.sgwannabig.smallgift.springboot.config.advice.exception.ShopNotFoundException;
import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.repository.ShopRepository;
import com.sgwannabig.smallgift.springboot.service.product.UpdateProductCommand;
import com.sgwannabig.smallgift.springboot.service.product.UpdateProductUsecase;
import com.sgwannabig.smallgift.springboot.util.FileDir;
import com.sgwannabig.smallgift.springboot.util.aws.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductService implements UpdateProductUsecase {

  private final ShopRepository shopRepository;
  private final ProductRepository productRepository;
  private final S3Manager s3Manager;
  @Override
  public Product updateProduct(UpdateProductCommand updateProductCommand) {
    Product foundedProduct = findProduct(updateProductCommand.getProductId());
    String productImageUrl = s3Manager.uploadFile(updateProductCommand.getProductImage(), FileDir.UPDATE_PRODUCT);
    s3Manager.deleteFile(foundedProduct.getProductImage());
    Product product = updateProductCommand.getProduct();
    product.setProductImage(productImageUrl);

    foundedProduct.updateProduct(product);
    return productRepository.save(foundedProduct);
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }
}
