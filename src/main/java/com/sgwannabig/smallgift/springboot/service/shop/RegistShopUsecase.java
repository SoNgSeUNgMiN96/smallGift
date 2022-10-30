package com.sgwannabig.smallgift.springboot.service.shop;

import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import java.util.function.Function;

public interface RegistShopUsecase extends Function<RegistShopCommand, Shop> {

}
