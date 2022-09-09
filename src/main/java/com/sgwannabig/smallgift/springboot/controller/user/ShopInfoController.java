package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.domain.Shop;
import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import com.sgwannabig.smallgift.springboot.dto.shop.*;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.repository.ShopRepository;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Component
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class ShopInfoController {

    @Autowired
    private final ShopRepository shopRepository;

    @Autowired
    private final ResponseService responseService;

    @Autowired
    private final ProductRepository productRepository;

    @ApiOperation(value = "shop/info/all/locate", notes = "가게를 지역구를 단위로 보내준다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역구", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/info/all/locate")
    public SingleResult<ShopAllByLocateResDto> allShopByLocate(@RequestParam("locate") String locate){

        List<Shop> allShopByLocate = shopRepository.findAllByShopAddressLike("%" + locate + "%");

        ShopAllByLocateResDto shopAllByLocateResDto = new ShopAllByLocateResDto(new ArrayList<>());


        if(allShopByLocate==null){
            return  responseService.getSingleResult(shopAllByLocateResDto);
        }

        IntStream.range(0,allShopByLocate.size()).forEach(i->{
            Shop shop = allShopByLocate.get(i);
            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .address(shop.getShopAddress())
                    .shopId(shop.getId())
                    .category(shop.getCategory())
                    .shopName(shop.getShopName())
                    .build();

            shopAllByLocateResDto.getShopAllByLocate().add(new KeyValueDto<>(i, shopInfoDto));
        });

        //원본 섞기인지 리턴인지 확인해야함
        Collections.shuffle(shopAllByLocateResDto.getShopAllByLocate());

        return responseService.getSingleResult(shopAllByLocateResDto);
    }

    @ApiOperation(value = "shop/info/all", notes = "가게를 전체를 보내준다.")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/info/all")
    public SingleResult<ShopAllByLocateResDto> allShop(){

        List<Shop> allShop = shopRepository.findAll();

        ShopAllByLocateResDto shopAllByLocateResDto = new ShopAllByLocateResDto(new ArrayList<>());


        if(allShop==null){
            return  responseService.getSingleResult(shopAllByLocateResDto);
        }

        IntStream.range(0,allShop.size()).forEach(i->{
            Shop shop = allShop.get(i);
            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .address(shop.getShopAddress())
                    .shopId(shop.getId())
                    .category(shop.getCategory())
                    .shopName(shop.getShopName())
                    .build();

            shopAllByLocateResDto.getShopAllByLocate().add(new KeyValueDto<>(i, shopInfoDto));
        });

        //원본 섞기인지 리턴인지 확인해야함
        Collections.shuffle(shopAllByLocateResDto.getShopAllByLocate());

        return responseService.getSingleResult(shopAllByLocateResDto);
    }



    @ApiOperation(value = "shop/info/best", notes = "지역구별 인기 가게 10개를 보여준다. (찜 총 갯수 기준)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역구", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 409, message = "해당 지역구 결과 없음."),
    })
    @GetMapping("shop/info/best")
    public SingleResult<ShopBestByLocateResDto> bestShopByLocate(@RequestParam("locate") String locate){

        List<Shop> topShopByLocate = shopRepository.findTop10ByShopAddressLikeOrderByTotalLikeDesc("%" + locate + "%");

        ShopBestByLocateResDto shopBestByLocateResDto = new ShopBestByLocateResDto(new ArrayList<>());


        if(topShopByLocate==null){
            return  responseService.getSingleResult(shopBestByLocateResDto);
        }

        IntStream.range(0,topShopByLocate.size()).forEach(i->{
            Shop shop = topShopByLocate.get(i);
            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .address(shop.getShopAddress())
                    .shopId(shop.getId())
                    .category(shop.getCategory())
                    .shopName(shop.getShopName())
                    .build();

            shopBestByLocateResDto.getTopShopByLocate().add(new KeyValueDto<>(i, shopInfoDto));
        });


        return responseService.getSingleResult(shopBestByLocateResDto);
    }


    @ApiOperation(value = "shop/details", notes = "선택한 가게의 모든 정보를 보내준다. 메뉴들 포함")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", value ="가게 Id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "가게 아이디가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/details")
    public SingleResult<ShopDetailsResDto> shopInfoAll(@RequestParam("locate") long shopId){

        Optional<Shop> shopOptional = shopRepository.findById(shopId);
        ShopDetailsResDto shopDetailsResDto = new ShopDetailsResDto();
        shopDetailsResDto.setShopAllByLocate(new ArrayList<>());

        AtomicReference<SingleResult<ShopDetailsResDto>> singleResult = null;

        shopOptional.ifPresentOrElse(shop -> {
            Optional<List<Product>> allByShopId = productRepository.findAllByShopId(shop.getId());

            shopDetailsResDto.setShopInfoDetailDto(ShopInfoDetailDto.builder()
                    .businessHours(shop.getBusinessHours())
                    .createShopDate(shop.getCreateShopDate())
                    .id(shop.getId())
                    .isAllowed(shop.isAllowed())
                    .shopAddress(shop.getShopAddress())
                    .shopName(shop.getShopName())
                    .shopTelephone(shop.getShopTelephone())
                    .mainMenu(shop.getMainMenu())
                    .category(shop.getCategory())
                    .build());

            allByShopId.ifPresent( shopList ->{

                IntStream.range(0, shopList.size()).forEach(i -> {
                    Product product = shopList.get(i);
                    shopDetailsResDto.getShopAllByLocate().add(new KeyValueDto<>(i, ProductInfoDto.builder()
                            .category(product.getCategory())
                            .createDate(product.getCreateDate())
                            .discountPrice(product.getDiscountPrice())
                            .endDate(product.getEndDate())
                            .id(product.getId())
                            .productName(product.getProductName())
                            .productPrice(product.getProductPrice())
                            .productStock(product.getProductStock())
                            .status(product.getStatus())
                            .startDate(product.getStartDate())
                            .build()));
                });
            });
            singleResult.set(responseService.getSingleResult(shopDetailsResDto));

        }, () -> {
            singleResult.set(responseService.getfailResult(409, shopDetailsResDto));
        });

        return singleResult.get();
    }



}
