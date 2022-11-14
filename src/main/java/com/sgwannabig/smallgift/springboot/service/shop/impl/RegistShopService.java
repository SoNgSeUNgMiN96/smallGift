package com.sgwannabig.smallgift.springboot.service.shop.impl;

import com.sgwannabig.smallgift.springboot.config.advice.exception.ManagerExistedException;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
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
    validateExistedManager(registShopCommand.getShop().getManager());
    return shopRepository.save(registShopCommand.getShop());
  }

  private void validateExistedManager(Manager manager) {
    shopRepository.findByManager(manager).ifPresent(m -> {
          throw new ManagerExistedException("이미 존재하는 매니저입니다");
        });
  }
}
