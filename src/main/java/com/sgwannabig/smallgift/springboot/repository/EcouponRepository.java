package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Ecoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EcouponRepository extends JpaRepository<Ecoupon, Long> {



    //user_id, orderdetails_id, orderdetails_id orderdetails_id
    List<Ecoupon> findByUserId(long userId);

    Ecoupon findByOrderDetailsId(long orderDetailsId);

    boolean existsByCouponNumber(String couponNumber);
}
