package com.sgwannabig.smallgift.springboot.service;

import com.sgwannabig.smallgift.springboot.config.advice.exception.*;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.dto.signup.MemberLoginRequestDto;
import com.sgwannabig.smallgift.springboot.dto.signup.MemberLoginResponseDto;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.service.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private JavaMailSender javaMailSender;

    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByUsername(requestDto.getUsername());
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();

        return new MemberLoginResponseDto(member.getId(), jwtTokenProvider.createToken(requestDto.getEmail()));
    }

    private Member saveManager(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByUsername(requestDto.getUsername());
        if(member == null) {
            member = new Member();
            member.setUsername(requestDto.getUsername());
            member.setEmail(requestDto.getEmail());
            member.setRole("ROLE_MANAGER");
            memberRepository.save(member);
        }
        return member;
    }

    public boolean checkUsernameDuplicate(String username) {
        return memberRepository.existsByUsername(username);
    }
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findById(username);
    }

    // 아이디 찾기 메소드
    public String findId(String email) {
        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            return null;
        }
        return member.getUsername();
    }

    // 비밀번호 찾기 메소드
    public Boolean resetPassword(String username, String email) {
        Member member = memberRepository.findById(username);
        // 아이디 검색 실패
        if (member == null)
            return false;
        // 이메일 검색 실패
        if (member.getEmail().equals(email) == false)
            return false;
        // 임시 비밀번호를 만들어 암호화한 다음 DB에 업데이트 후 이메일로 임시 비밀번호 발송
        String newPassword = "flwkejf";
        String newEncodedPassword = passwordEncoder.encode(newPassword);
        memberRepository.save(member);
        String text = new StringBuilder("<h1>임시 비밀번호</h1>")
                .append("<p>아래 임시 비밀번호로 로그인해 주세요. 로그인 후 비밀번호를 변경해 주세요.</p>")
                .append(newPassword).toString();
        sendMail("admin@naver.com", member.getEmail(), "임시 비밀번호", text);
        return true;
    }

    private void sendMail(String from, String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, false, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //  비밀번호 변경 메소드
    @Transactional
    public Long changePassword(String username, String password) {
        Member oneMember = findMemberByUsername(username);
        oneMember.setPassword(password);
        return oneMember.getId();
    }

    //    회원 탈퇴 기능 메소드
    @Transactional
    public Long deleteById(Long id) {
        memberRepository.deleteById(id);
        return id;
    }


}
