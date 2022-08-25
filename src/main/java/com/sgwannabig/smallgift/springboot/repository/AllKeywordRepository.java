package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.AllKeyword;
import com.sgwannabig.smallgift.springboot.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllKeywordRepository extends JpaRepository<AllKeyword, Long> {
    AllKeyword findById(String id);
    AllKeyword findByKeyword(String keyword);
    List<AllKeyword> findTop10ByKeywordLikeOrderByCountDesc(String keyword);    //검색어를 포함한 메뉴들 Top10
    List<AllKeyword> findTop10ByOrderByCountDesc();     //가장 인기있는 키워드 Top10
}
