package com.sgwannabig.smallgift.springboot.service;

import com.sgwannabig.smallgift.springboot.config.advice.exception.*;
import com.sgwannabig.smallgift.springboot.domain.EmailAuth;
import com.sgwannabig.smallgift.springboot.domain.Member;

import com.sgwannabig.smallgift.springboot.dto.signup.*;
import com.sgwannabig.smallgift.springboot.repository.EmailAuthRepository;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;

    private final EmailService emailService;


    // 회원가입
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail());
        EmailAuth emailAuth = emailAuthRepository.save(
                        EmailAuth.builder()
                                .email(requestDto.getEmail())
                                .authToken(UUID.randomUUID().toString())
                                .expired(false)
                                .build());

        Member member = memberRepository.save(
                Member.builder()
                        .username(null)
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .emailAuth(false)
                        .build());

        emailService.send(emailAuth.getEmail(), emailAuth.getAuthToken());
        return MemberRegisterResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .authToken(emailAuth.getAuthToken())
                .build();
    }


    // 아이디 중복확인
    public void validateDuplicated(String email) {
        if(memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
    }

    /*
     * 이메일 인증 성공
     */

    @Transactional
    public void confirmEmail(EmailAuthRequestDto requestDto) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getAuthToken(), LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFoundException::new);
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        emailAuth.useToken();
        member.emailVerifiedSuccess();
    }

    // 로그인 (token 발급)
    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();
        if (!member.getEmailAuth())
            throw new EmailNotAuthenticatedException();
        member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
        return new MemberLoginResponseDto(member.getId(), jwtTokenProvider.createToken(requestDto.getEmail()), member.getRefreshToken());
    }


    private Member saveMember(ProfileDto profile) {
        Member member = Member.builder()
                .email(profile.getEmail())
                .password(null)
                .build();
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    /*
     토큰 재발행
     @param requestDto
      @return
     */

    @Transactional
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        if (!jwtTokenProvider.validateTokenExpiration(requestDto.getRefreshToken()))
            throw new InvalidRefreshTokenException();

        Member member = findMemberByToken(requestDto);

        if (!member.getRefreshToken().equals(requestDto.getRefreshToken()))
            throw new InvalidRefreshTokenException();

        String accessToken = jwtTokenProvider.createToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(refreshToken);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    public Member findMemberByToken(TokenRequestDto requestDto) {
        Authentication auth = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        return memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new);
    }

}
