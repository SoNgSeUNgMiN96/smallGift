package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.OrderDetails;
import com.sgwannabig.smallgift.springboot.domain.RefreshToken;
import com.sgwannabig.smallgift.springboot.domain.RefundDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundDetailsRepository extends JpaRepository<RefundDetails, Long> {

    List<RefundDetails> findAllByUserId(long userId);

}
