package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import java.util.function.Function;

public interface RegistProductUsecase extends Function<RegistProductCommand, Product> {

}
