package com.sgwannabig.smallgift.springboot.service.shop;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import lombok.Getter;

@Getter
public class RegistShopCommand {

  private Shop shop;

  public RegistShopCommand(Manager manager) {
    this.shop = Shop.builder()
        .shopName(manager.getBusinessName())
        .shopAddress(manager.getAddress())
        .shopTelephone(manager.getBusinessTel())
        .isAllowed(true)
        .manager(manager)
        .build();
  }
}
