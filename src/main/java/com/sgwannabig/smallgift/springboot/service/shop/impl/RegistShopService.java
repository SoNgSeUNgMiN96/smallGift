package com.sgwannabig.smallgift.springboot.service.shop.impl;

import com.sgwannabig.smallgift.springboot.domain.Shop;
import com.sgwannabig.smallgift.springboot.repository.ShopRepository;
import com.sgwannabig.smallgift.springboot.service.shop.RegistShopCommand;
import com.sgwannabig.smallgift.springboot.service.shop.RegistShopUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistShopService implements RegistShopUsecase {

  private final ShopRepository shopRepository;

  @Override
  public Shop apply(RegistShopCommand registShopCommand) {
    return shopRepository.save(registShopCommand.getShop());
  }
}
