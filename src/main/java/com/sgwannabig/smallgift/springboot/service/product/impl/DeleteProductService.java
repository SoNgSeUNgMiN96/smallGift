package com.sgwannabig.smallgift.springboot.service.product.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ProductNotFoundException;
import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.service.product.DeleteProductUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteProductService implements DeleteProductUsecase {

  private final ProductRepository productRepository;

  @Override
  public void deleteProduct(Long productId) {
    productRepository.delete(findProduct(productId));
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }

}
