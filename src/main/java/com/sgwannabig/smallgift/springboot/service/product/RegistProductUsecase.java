package com.sgwannabig.smallgift.springboot.service.product;

import com.sgwannabig.smallgift.springboot.domain.Product;
import java.util.function.Function;

public interface RegistProductUsecase extends Function<RegistProductCommand, Product> {

}
