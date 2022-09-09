package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findById(String id);
    //지역명을 포함하여, 가장 인기있는 가게 Top10을 뽑아준다.  %서울%등으로 검색
    List<Shop> findTop10ByShopAddressLikeOrderByTotalLikeDesc(String shopAddress);

    //지역을 기준으로 모든 가게를 반환한다.
    List<Shop> findAllByShopAddressLike(String shopAddress);
}



//findTop10ByKeywordLikeOrderByCountDesc