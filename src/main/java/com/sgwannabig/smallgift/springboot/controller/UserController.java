package com.sgwannabig.smallgift.springboot.controller;


import com.sgwannabig.smallgift.springboot.dto.shop.ShopMenuDto;
import com.sgwannabig.smallgift.springboot.dto.user.UserLocateDto;
import com.sgwannabig.smallgift.springboot.dto.user.UserRefundAccountDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {


    @ApiOperation(value = "/set/locate", notes = "유저의 좌표를 잡아줍니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="유저 설정 주소", required = true),
            @ApiImplicitParam(name="userId", value ="유저 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/set/locate")
    public String setUserLocate(@RequestBody UserLocateDto userLocateDto){

        return "success";
    }


    @ApiOperation(value = "/set/account", notes = "유저의 환불 계좌를 입력합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value ="유저 아이디", required = true),
            @ApiImplicitParam(name="accountBank", value ="은행명", required = true),
            @ApiImplicitParam(name="accountNumber", value ="계좌번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/set/account")
    public String shopDetails(@RequestBody UserRefundAccountDto userRefundAccountDto){

        return "success";
    }




}
