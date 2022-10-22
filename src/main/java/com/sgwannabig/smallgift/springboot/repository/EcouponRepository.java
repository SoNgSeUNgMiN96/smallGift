package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Ecoupon;
import com.sgwannabig.smallgift.springboot.domain.Payment;
import com.sgwannabig.smallgift.springboot.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EcouponRepository extends JpaRepository<Ecoupon, Long> {



    //user_id, orderdetails_id, orderdetails_id orderdetails_id
    List<Ecoupon> findByUserId(long userId);
}
