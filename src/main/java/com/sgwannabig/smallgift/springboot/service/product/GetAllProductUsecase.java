package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import java.util.List;

public interface GetAllProductUsecase {
  List<Product> getAllProduct(Long shopId);
}
