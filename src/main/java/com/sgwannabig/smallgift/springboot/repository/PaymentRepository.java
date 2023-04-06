package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
