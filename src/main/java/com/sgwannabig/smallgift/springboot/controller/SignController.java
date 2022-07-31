package com.sgwannabig.smallgift.springboot.controller;

import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.result.SingleResult;
import com.sgwannabig.smallgift.springboot.dto.signup.*;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.SignService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/manager")
public class SignController { // MemberController
    private final SignService signService;
    private final ResponseService responseService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행한다.") // 구현 O
    @PostMapping("signup")
    public SingleResult<MemberRegisterResponseDto> signup(@RequestBody MemberRegisterRequestDto requestDto) {
        log.info("request ={}, {}", requestDto.getEmail(), requestDto.getPassword());
        MemberRegisterResponseDto responseDto = signService.registerMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴를 진행한다.") // 구현 O
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "회원 탈퇴에 실패했습니다."),
    })
    @DeleteMapping("/{managerNum}")
    public String resign(@PathVariable Long managerNum) {
        Optional<Member> member = memberRepository.findById(managerNum);
        member.ifPresent(selectMember -> {
            memberRepository.delete(selectMember);
        });
        if(member != null) return "회원탈퇴 성공";
        else return "회원탈퇴에 실패하였습니다.";
    }

    @ApiOperation(value = "이메일 인증", notes = "이메일 인증을 진행한다.")  // 구현 O
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "관리자 이메일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "올바른 이메일 형식이 아닙니다."),
    })
    @GetMapping("/confirm-email")
    public SingleResult<String> confirmEmail(@ModelAttribute EmailAuthRequestDto requestDto) {
        signService.confirmEmail(requestDto);
        return responseService.getSingleResult("인증이 완료되었습니다.");
    }

    @ApiOperation(value = "로컬 로그인", notes = "로컬을 통해 로그인을 진행한다.") // 구현 O
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "관리자 아이디", required = true),
            @ApiImplicitParam(name = "password", value = "관리자 패스워드", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @GetMapping("login")
    public SingleResult<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @GetMapping("/logout")
    public String logout(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return "<h1>홈으로</h1>";
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기를 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "관리자 이메일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "아이디 찾기 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @PostMapping("/findId")
    public SingleResult<MemberLoginResponseDto> findId(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "아이디 중복체크", notes = "아이디 중복 확인을 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "관리자 아이디", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "아이디 중복 확인 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "중복된 아이디 입니다.")
    })
    @PostMapping("/idCheck")
    public SingleResult<MemberLoginResponseDto> idCheck(@RequestBody MemberLoginRequestDto requestDto) throws Exception{
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기를 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "관리자 아이디", required = true),
            @ApiImplicitParam(name = "email", value = "관리자 이메일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "비밀번호 찾기 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "이메일 형식이 올바르지 않습니다.")
    })
    @PostMapping("/findPwd")
    public SingleResult<MemberLoginResponseDto> findPwd(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 변경한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "관리자 아이디", required = true),
            @ApiImplicitParam(name = "password", value = "관리자 패스워드", required = true),
            @ApiImplicitParam(name = "newPassword", value = "새로운 비밀번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "비밀번호 변경 완료"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @PostMapping("/changePwd")
    public SingleResult<MemberLoginResponseDto> changePwd(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "토큰 재발급", notes = "Refresh Token을 통해 재발급받는다.") // 구현 O
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "토큰 시간이 만료됨."),
    })
    @PostMapping("/reissue")
    public SingleResult<TokenResponseDto> reIssue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenResponseDto responseDto = signService.reIssue(tokenRequestDto);
        return responseService.getSingleResult(responseDto);
    }
}
