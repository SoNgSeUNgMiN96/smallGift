package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Ecoupon;
import com.sgwannabig.smallgift.springboot.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcouponRepository extends JpaRepository<Ecoupon, Long> {
}
