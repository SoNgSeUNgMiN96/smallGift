package com.sgwannabig.smallgift.springboot.repository;

import com.sgwannabig.smallgift.springboot.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    Member findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Member findById(String id);
}