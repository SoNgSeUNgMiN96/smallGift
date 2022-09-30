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

    @ApiOperation(value = "shop/info/all/locate", notes = "가게를 지역 단위로 보내준다. <- 서울 특별시 마포구 등으로 해서 넣어줘야함. 메인페이지에 지역 설정해서 추천 나오는 부분에 활용하면될듯")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/info/all/locate")
    public SingleResult<ShopAllByLocateResDto> allShopByLocate(@RequestParam("locate") String locate){

        List<Shop> allShopByLocate = shopRepository.findAllByShopAddressLike("%" + locate + "%");

        ShopAllByLocateResDto shopAllByLocateResDto = new ShopAllByLocateResDto(new ArrayList<>(),1);



        if(allShopByLocate==null){
            return  responseService.getSingleResult(shopAllByLocateResDto);
        }


        IntStream.range(0,allShopByLocate.size()).forEach(i->{
            Shop shop = allShopByLocate.get(i);

            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .address(shop.getShopAddress())
                    .mainMenu(shop.getMainMenu())
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


    @ApiOperation(value = "shop/info/all/locate/category", notes = "근처 가게를 보내준다. locate에 지역을 넣어주고,  category에 전체, 한식, 일식, 중식, 양식, 카페 등을 설정하고, page에 페이지번호, pagePerCount에 페이지당 요청수를 넣어준다. ")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역", required = true),
            @ApiImplicitParam(name="category", value ="카테고리", required = true),
            @ApiImplicitParam(name="page", value ="페이지", required = true),
            @ApiImplicitParam(name="pagePerCount", value ="페이지당요청수", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/info/all/locate/category")
    public SingleResult<ShopAllByLocateResDto> allShop(@RequestParam("locate") String locate,@RequestParam("category") String category, @RequestParam("page")  int page, @RequestParam("pagePerCount")  int pagePerCount ){

        List<Shop> allShop = shopRepository.findAll();


        switch (category){
            case "전체":
                //locate 설정해야함
                allShop = shopRepository.findAllByShopAddressLikeOrderByTotalLikeDesc(locate);
                break;
            default:
                allShop = shopRepository.findAllByShopAddressLikeAndCategoryLikeOrderByTotalLikeDesc(locate, category);
                break;
        }



        int maxPage = (int) Math.ceil(allShop.size() / (double) pagePerCount);
        maxPage = maxPage == 0 ? 1 : maxPage;     //리스트가 0개일 경우 예외처리

        page = page > maxPage ? maxPage : page;
        int end = page * pagePerCount, start = (page - 1) * pagePerCount;



        ShopAllByLocateResDto shopAllByLocateResDto = new ShopAllByLocateResDto(new ArrayList<>(), page);


        if(allShop==null){
            return  responseService.getSingleResult(shopAllByLocateResDto);
        }


        List<Shop> finalAllShop = allShop.subList(start,end);


        IntStream.range(0,allShop.size()).forEach(i->{
            Shop shop = finalAllShop.get(i);
            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .address(shop.getShopAddress())
                    .shopId(shop.getId())
                    .category(shop.getCategory())
                    .shopName(shop.getShopName())
                    .build();

            shopAllByLocateResDto.getShopAllByLocate().add(new KeyValueDto<>(i, shopInfoDto));
        });


        return responseService.getSingleResult(shopAllByLocateResDto);
    }



    @ApiOperation(value = "shop/info/best", notes = "지역(서울시 강남구 역삼동 등 주소를 넣어주면 됨.)별 인기 가게 3개를 보여준다. (찜 총 갯수 기준)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 409, message = "해당 지역구 결과 없음."),
    })
    @GetMapping("shop/info/best")
    public SingleResult<ShopBestByLocateResDto> bestShopByLocate(@RequestParam("locate") String locate) {

        List<Shop> topShopByLocate = shopRepository.findTop3ByShopAddressLikeOrderByTotalLikeDesc("%" + locate + "%");

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


    @ApiOperation(value = "shop/details", notes = "선택한 가게의 모든 정보를 보내준다. 가게 운영시간 포함")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", value ="가게 Id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "가게 아이디가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/details")
    public SingleResult<ShopDetailsResDto> shopInfoAll(@RequestParam("shopId") long shopId){

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


    @ApiOperation(value = "shop/menu", notes = "선택한 가게의 모든 메류를 보내준다")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shopId", value ="가게 Id", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "가게 아이디가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/menu")
    public SingleResult<ShopAllMenuDto> shopMenuAll(@RequestParam("shopId") long shopId){

        Optional<Shop> shopOptional = shopRepository.findById(shopId);
        ShopAllMenuDto shopDetailsResDto = ShopAllMenuDto.builder()
                .shopAllMenuList(new ArrayList<>()).build();


        AtomicReference<SingleResult<ShopAllMenuDto>> singleResult = null;

        shopOptional.ifPresentOrElse(shop -> {

            Optional<List<Product>> allMenuByShopId = productRepository.findAllByShopId(shop.getId());

            allMenuByShopId.ifPresent( menu ->{

                IntStream.range(0, menu.size()).forEach(i -> {
                    Product product = menu.get(i);
                    shopDetailsResDto.getShopAllMenuList().add(new KeyValueDto<>(i, ProductInfoDto.builder()
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
