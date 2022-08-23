package com.sgwannabig.smallgift.springboot.controller;

import com.sgwannabig.smallgift.springboot.dto.sales.ShopProductDto;
import com.sgwannabig.smallgift.springboot.dto.settlement.SalesStatusRequestDto;
import com.sgwannabig.smallgift.springboot.dto.settlement.SalesStatusResponseDto;
import com.sgwannabig.smallgift.springboot.service.SalesStatusService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class SalesStatusController {
    private final SalesStatusService salesStatusService;

    @ApiOperation(value = "판매 현황", notes = "관리자 판매 내역 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/sales/status")
    public SalesStatusResponseDto salesDetails(@RequestBody SalesStatusRequestDto salesStatusRequestDto) throws ParseException {
        SalesStatusResponseDto salesStatusResponseDto = salesStatusService.salesStatus(salesStatusRequestDto);
        if(salesStatusResponseDto == null) {
            return null;
        }
        return salesStatusResponseDto;
    }
}
