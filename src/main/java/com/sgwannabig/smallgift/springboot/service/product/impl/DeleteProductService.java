package com.sgwannabig.smallgift.springboot.service.product.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ProductNotFoundException;
import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.service.product.DeleteProductUsecase;
import com.sgwannabig.smallgift.springboot.util.aws.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteProductService implements DeleteProductUsecase {

  private final ProductRepository productRepository;
  private final S3Manager s3Manager;

  @Override
  public void deleteProduct(Long productId) {
    Product foundedProduct = findProduct(productId);
    s3Manager.deleteFile(foundedProduct.getProductImage());
    productRepository.delete(foundedProduct);
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }

}
