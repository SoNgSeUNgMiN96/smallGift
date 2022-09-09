package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.dto.shop.ShopInfoDto;
import com.sgwannabig.smallgift.springboot.dto.shop.ShopMenuDto;
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
public class ShopInfoController {


    @ApiOperation(value = "shop/info/all/{locate}", notes = "가게를 지역구를 단위로 보내준다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역구", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/info/all/{locate}")
    public List<ShopInfoDto> shopInfoAll(@PathVariable("locate") String locate){

        List<ShopInfoDto> shopByLocate = new ArrayList<>();
        return shopByLocate;
    }


    @ApiOperation(value = "shop/details/menu/{shopId}", notes = "가게의 메뉴정보들을 보내준다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", value ="샵 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/details/menu/{shopId}")
    public List<ShopMenuDto> shopDetailsMenu(@PathVariable("shopId") String shopId){

        List<ShopMenuDto> menuByShop = new ArrayList<>();
        return menuByShop;
    }

    //businessHours

    @ApiOperation(value = "shop/details/{shopId}", notes = "가게의 메뉴정보들을 보내준다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", value ="샵 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/details/{shopId}")
    public List<ShopMenuDto> shopDetails(@PathVariable("shopId") String shopId){

        List<ShopMenuDto> menuByShop = new ArrayList<>();

        return menuByShop;
    }
}
