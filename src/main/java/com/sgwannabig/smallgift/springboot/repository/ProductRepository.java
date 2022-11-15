package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findAllByShopId(Long shopId);



    List<Product> findByShopId(Long ShopId);
    List<Product> findByCreateDateBetween(Date fromDate, Date toDate);
    List<Product> findByProductName(String productName);
}
