package com.sgwannabig.smallgift.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.repository.RefreshTokenRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/manager/sales/management")
@RequiredArgsConstructor
public class SalesManagementController {
    private final MemberRepository memberRepository;
    //private final RefreshTokenRepository refreshTokenRepository;

    PasswordEncoder passwordEncoder;
    ObjectMapper om = new ObjectMapper();

    @Autowired
    public SalesManagementController(MemberRepository memberRepository, RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        //this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    @ApiOperation(value = "사업자 등록", notes = "사업자 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="대표자명", required = true),
            @ApiImplicitParam(name="businessName", value ="상호명", required = true),
            @ApiImplicitParam(name="address", value ="사업자 주소", required = true),
            @ApiImplicitParam(name="businessTel", value ="사업자 등록 번호", required = true),
            @ApiImplicitParam(name="businessType", value ="업종", required = true),
            @ApiImplicitParam(name="bankAccount", value ="계좌", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
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
            @ApiResponse(code = 200, message = "성공"),
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
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/products")
    public String productManagement() {
        return "<h1>products</h1>";
    }
}
