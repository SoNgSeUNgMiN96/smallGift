package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.OrderDetails;
import com.sgwannabig.smallgift.springboot.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderdetailsRepository extends JpaRepository<OrderDetails, Long> {

   //List<AllKeyword> findTop10ByKeywordLikeOrderByCountDesc(String keyword);    //검색어를 포함한 메뉴들 Top10
    List<OrderDetails> findAllByUserId(long userId);
}

