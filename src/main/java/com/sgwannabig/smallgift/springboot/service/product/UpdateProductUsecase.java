package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;

public interface UpdateProductUsecase {

  Product updateProduct(UpdateProductCommand updateProductCommand);

}
