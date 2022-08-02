package com.sgwannabig.smallgift.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.repository.RefreshTokenRepository;
import com.sgwannabig.smallgift.springboot.service.MemberService;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Component
@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResponseService responseService;
    private final MemberService memberService;

    PasswordEncoder passwordEncoder;
    ObjectMapper om = new ObjectMapper();

    @ApiOperation(value = "관리자 메인", notes = "관리자 메인 페이지입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음")
    })
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
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
    public @ResponseBody
    String changePassword(Principal principal, @RequestParam("password") String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member findMember = memberService.findMemberByUsername(principal.getName());
        findMember.setPassword(newPassword);
        Long resultId = memberService.changePassword(findMember.getUsername(), passwordEncoder.encode(newPassword));
        return "비밀번호 수정 완료";
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @GetMapping("/logout")
    public String logout() {
        return "로그아웃 성공";
    }

    @ApiOperation(value = "사업자 등록", notes = "사업자 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="대표자명", required = true),
            @ApiImplicitParam(name="businessName", value ="상호명", required = true),
            @ApiImplicitParam(name="address", value ="사업자 주소", required = true),
            @ApiImplicitParam(name="businessTel", value ="사업자 등록 번호", required = true),
            @ApiImplicitParam(name="businessType", value ="업종", required = true),
            @ApiImplicitParam(name="bankName", value ="은행", required = true),
            @ApiImplicitParam(name="bankAccount", value ="계좌", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"username\": 홍길동,\n" +
                    "    \"businessName\": \"홍짜장\"\n" +
                    "    \"address\": \"경기도 안양시 만안구 XXX\"\n" +
                    "    \"businessTel\": \"999-99-000949\"\n" +
                    "    \"businessType\": \"음식점\"\n" +
                    "    \"bankName\": \"농협\"\n" +
                    "    \"bankAccount\": \"110-484-082450\"\n" +
                    "}"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 사업자입니다."),
            @ApiResponse(code = 402, message = "등록 번호 형식을 유지해주세요."),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("/registraion/proprietor")
    public String  proprietorRegistration() { //proprietorResponseDto
        return "사업자 등록 API";
    }

    @ApiOperation(value = "상품 등록", notes = "상품 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="category", value ="카테고리", required = true),
            @ApiImplicitParam(name="productName", value ="상품명", required = true),
            @ApiImplicitParam(name="productPrice", value ="상품 가격", required = true),
            @ApiImplicitParam(name="productStock", value ="재고 수량", required = true),
            @ApiImplicitParam(name="salesPeriod", value ="판매기간", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"category\": 한식,\n" +
                    "    \"productName\": \"김치찌개\"\n" +
                    "    \"productPrice\": \"7000\"\n" +
                    "    \"productStock\": \"15\"\n" +
                    "    \"salesPeriod\": \"2023/08/02\"\n" +
                    "}"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 상품입니다."),
            @ApiResponse(code = 402, message = "이미지 형식을 유지해주세요."),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("/registraion/product")
    public String productRegistration() { //productResponseDto
        return "상품 등록 API";
    }

    @ApiOperation(value = "상품 관리", notes = "상품 관리 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"productId\": 번호,\n" +
                    "    \"productName\": \"베이비 아포가토\"\n" +
                    "    \"productPrice\": \"7000\"\n" +
                    "    \"onProduct\": \"yes\"\n" +
                    "}"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/productList")
    public String productManagement() {
        return "<h1>상품리스트</h1>";
    }

    @ApiOperation(value = "상품 노출", notes = "상품 노출 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productId", value ="상품 코드", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PatchMapping("/productList/on/{productId}")
    public String onProduct(@PathVariable String productId) {
        return "상품 노출 여부를 확인해주세요";
    }

    @ApiOperation(value = "상품 삭제", notes = "상품 삭제 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "상품 삭제 완료"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @DeleteMapping("/productList/remove/{productId}")
    public String removeProduct(@PathVariable String productId) {
        return "상품 삭제 완료";
    }

    /*
    @ApiOperation(value = "상품 수정", notes = "상품 수정 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "상품 삭제 완료"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/productList/{productId}/modification")
    public String productModification(@PathVariable String productId) {
        return "상품 수정 페이지";
    }
*/
}
