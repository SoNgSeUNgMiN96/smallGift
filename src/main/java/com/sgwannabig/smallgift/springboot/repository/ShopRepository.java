package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findById(String id);

    //지역명을 포함하여, 가장 인기있는 가게 Top10을 뽑아준다.  %서울%등으로 검색
    List<Shop> findTop3ByShopAddressLikeOrderByTotalLikeDesc(String shopAddress);

    //지역을 기준으로 모든 가게를 반환한다.
    List<Shop> findAllByShopAddressLike(String shopAddress);

    Optional<Shop> findByManager(Manager manager);
    Optional<Shop> findByManagerId(Long managerId);

    //지역을 기준으로 모든 가게를 반환한다.
    List<Shop> findAllByShopAddressLikeOrderByTotalLikeDesc(String shopAddress);

    //지역과 카테고리를 기준으로 모든 가게를 반환한다.
    List<Shop> findAllByShopAddressLikeAndCategoryLikeOrderByTotalLikeDesc(String shopAddress, String category);

    //가게명으로 가게를 찾는다
    List<Shop> findAllByShopNameLike(String shopName);

}



//findTop10ByKeywordLikeOrderByCountDesc