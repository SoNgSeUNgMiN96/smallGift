package com.sgwannabig.smallgift.springboot.service.product.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ShopNotFoundException;
import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.domain.Shop;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.repository.ShopRepository;
import com.sgwannabig.smallgift.springboot.service.product.RegistProductCommand;
import com.sgwannabig.smallgift.springboot.service.product.RegistProductUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistProductService implements RegistProductUsecase {

  private final ProductRepository productRepository;
  private final ShopRepository shopRepository;

  @Override
  public Product apply(RegistProductCommand registProductCommand) {
    Shop shop = findShop(registProductCommand.getShopId());
    Product product = registProductCommand.getProduct();
    product.setShop(shop);
    return productRepository.save(product);
  }

  private Shop findShop(Long id) {
    return shopRepository.findById(id)
        .orElseThrow(() -> new ShopNotFoundException("해당 가게가 존재하지 않습니다"));

  }
}
