package com.sgwannabig.smallgift.springboot.config.auth;

import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService : 진입");
        Member member = memberRepository.findByUsername(username);

        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(member);
    }
}