package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.User;
import com.sgwannabig.smallgift.springboot.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<List<WishList>> findByUserId(long userId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
