package com.sgwannabig.smallgift.springboot.service.product.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ShopNotFoundException;
import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.repository.ShopRepository;
import com.sgwannabig.smallgift.springboot.service.product.GetAllProductUsecase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllProductService implements GetAllProductUsecase {

  private final ProductRepository productRepository;
  private final ShopRepository shopRepository;
  @Override
  public List<Product> getAllProduct(Long shopId) {
    Shop shop = findShop(shopId);
    return productRepository.findByShopId(shop.getId());
  }

  private Shop findShop(Long id) {
    return shopRepository.findById(id)
        .orElseThrow(() -> new ShopNotFoundException("해당 가게가 존재하지 않습니다"));
  }
}
