package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String userId);
    boolean existsById(String userId);
    User findByMemberId(long memberId);
}
