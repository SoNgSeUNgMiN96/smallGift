package com.sgwannabig.smallgift.springboot.controller;

import com.sgwannabig.smallgift.springboot.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SignController {
    private final SignService signService;
    private final ResponseService responseService;

    // 회원가입
    @PostMapping("/signup")
    public SingleResult<MemberRegisterResponseDto> signup(@RequestBody MemberRegisterRequestDto requestDto) {
        MemberRegisterResponseDto responseDto = signService.registerMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    // 로그인
    @PostMapping("/login")
    public SingleResult<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @PostMapping("/reissueAccessToken")
    public SingleResult<TokenResponseDto> reIssue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenResponseDto responseDto = signService.reIssue(tokenRequestDto);
        return responseService.getSingleResult(responseDto);
    }
}
