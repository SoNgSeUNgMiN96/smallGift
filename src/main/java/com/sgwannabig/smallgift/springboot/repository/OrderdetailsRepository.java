package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.OrderDetails;
import com.sgwannabig.smallgift.springboot.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderdetailsRepository extends JpaRepository<OrderDetails, Long> {

}

