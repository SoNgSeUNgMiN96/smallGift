package com.sgwannabig.smallgift.springboot.controller;

import com.sgwannabig.smallgift.springboot.dto.sales.ShopProductDto;
import com.sgwannabig.smallgift.springboot.dto.settlement.SettlementMessageDto;
import com.sgwannabig.smallgift.springboot.dto.settlement.SettlementResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class SalesStatusController {
    // private SalesStatusService salesStatusService;

    @ApiOperation(value = "환불 내역", notes = "사용자 환불 내역 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/refund/{managerId}")
    public List<ShopProductDto> getRefundList(@PathVariable Long managerId) {
        List<ShopProductDto> shopProductDtos = new ArrayList<>();
        return shopProductDtos;
    }

    @ApiOperation(value = "정산 내역", notes = "관리자 정산 내역 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/settlement/{managerId}")
    public List<SettlementResponseDto> getSettlementList(@PathVariable Long managerId) {
        List<SettlementResponseDto> settlementResponseDtos = new ArrayList<>();
        return settlementResponseDtos;
    }

    @ApiOperation(value = "정산 처리", notes = "관리자 정산 처리 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("/settlement/process/{managerId}")
    public List<SettlementMessageDto> settlementProcessing(@PathVariable Long managerId) { // ResponseEntity
        List<SettlementMessageDto> settlementMessageDtos = new ArrayList<>();  // 정산 처리 DTO 추후 생성
        return settlementMessageDtos;
    }
}
