package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {
    UserKeyword findById(String userKeywrodId);
    boolean existsByUserIdAndKeyword(long userId, String keyword);
    List<UserKeyword> findTop10ByUserIdOrderByModifiedDateDesc(long userId);
    List<UserKeyword> findAllByUserId(long userId);
}


