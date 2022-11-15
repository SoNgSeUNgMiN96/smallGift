package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
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

import java.util.*;
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




    //메인페이지 지역별 추천 메뉴로 바꿔야할듯
    @ApiOperation(value = "shop/info/all/locate", notes = "가게를 지역 단위로 보내준다. <- 서울 특별시 마포구 등으로 해서 넣어줘야함. 메인페이지에 지역별 추천메뉴")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="지역", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("shop/info/all/locate")
    public SingleResult<MenuRandomByLocateResDto> allShopByLocate(@RequestParam("locate") String locate){

        List<Shop> allShopByLocate = shopRepository.findAllByShopAddressLike("%" + locate + "%");

        MenuRandomByLocateResDto menuRandomByLocateResDto = new MenuRandomByLocateResDto(new ArrayList<>());



        if(allShopByLocate==null){
            return  responseService.getSingleResult(menuRandomByLocateResDto);
        }


        IntStream.range(0,allShopByLocate.size()).forEach(i->{
            Shop shop = allShopByLocate.get(i);

            List<Product> allMenuByShop = productRepository.findAllByShopId(shop.getId()).get();

            if(allMenuByShop!=null) {
                Collections.shuffle(allMenuByShop);

                Product product = allMenuByShop.get(0);

                //가게이름, 상품이름, 가격, 할인가, 할인율

                RandomMenuDto randomMenuDto = RandomMenuDto.builder()
                        .shopName(shop.getShopName())
                        .discountPrice(product.getDiscountPrice())
                        .price(product.getProductPrice())
                        .discountRate(product.getDiscountPrice()/(double)product.getProductPrice())
                        .image(product.getProductImage())
                        .build();

                menuRandomByLocateResDto.getMenuRandomByLocateResDto().add(new KeyValueDto<>(i, randomMenuDto));
            }
        });

        if (menuRandomByLocateResDto.getMenuRandomByLocateResDto().size() > 3) {
            menuRandomByLocateResDto.setMenuRandomByLocateResDto(menuRandomByLocateResDto.getMenuRandomByLocateResDto().subList(0, 3));
        }

        return responseService.getSingleResult(menuRandomByLocateResDto);
    }




    @ApiOperation(value = "shop/info/all/locate/category", notes = "근처 가게를 보내준다. locate에 지역을 넣어주고,  category에 전체, 한식, 일식, 중식, 양식, 카페 등을 설정하고, page에 페이지번호, pagePerCount에 페이지당 요청수를 넣어준다. <<카테고리 탐색 페이지")
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

        List<Shop> allShop, allshopSub;


        String locateQuery;
        if(locate.equals("")){
            locateQuery = "%";
        }else {
            locateQuery = "%" + locate + "%";
        }

        switch (category){
            case "전체":
                //locate 설정해야함
                allShop = shopRepository.findAllByShopAddressLikeOrderByTotalLikeDesc(locateQuery);
                break;

            default:
                allShop = shopRepository.findAllByShopAddressLikeAndCategoryLikeOrderByTotalLikeDesc(locateQuery, category);

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

        //최대갯수 예외처리
        end = (end>allShop.size())? allShop.size() : end;

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




    //지역별 인기 상점 . 서울/경기 수정완료
    @ApiOperation(value = "shop/info/best", notes = "지역(서울시 강남구 역삼동 등 주소를 넣어주면 됨.)별 인기 가게 3개를 보여준다. (찜 총 갯수 기준) <<메인페이지 하단  ")
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

        List<Shop> topShopByLocate;

        String query;

        if(locate.equals("서울/경기")){
            topShopByLocate = shopRepository.findTop3ByShopAddressLikeOrderByTotalLikeDesc("%서울%");
            List<Shop> bestSub = shopRepository.findTop3ByShopAddressLikeOrderByTotalLikeDesc("%경기%");
            topShopByLocate.addAll(bestSub);

            topShopByLocate.sort((s1,s2)->{
                return (int) (s2.getTotalLike() - s1.getTotalLike());
            });


            topShopByLocate = topShopByLocate.subList(0, topShopByLocate.size()>3? 3 : topShopByLocate.size() );

        }else{
            query = "";
            if(locate.equals("")){
                query = "%";
            }else{
                query = "%" + locate + "%";
            }
            topShopByLocate = shopRepository.findTop3ByShopAddressLikeOrderByTotalLikeDesc(query);
        }


        ShopBestByLocateResDto shopBestByLocateResDto = new ShopBestByLocateResDto(new ArrayList<>());

        if(topShopByLocate==null){
            return  responseService.getSingleResult(shopBestByLocateResDto);
        }

        List<Shop> finalTopShopByLocate = topShopByLocate;

        IntStream.range(0,topShopByLocate.size()).forEach(i->{
            Shop shop = finalTopShopByLocate.get(i);
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
    public SingleResult<ShopInfoDetailDto> shopInfoAll(@RequestParam("shopId") long shopId){

        Optional<Shop> shopOptional = shopRepository.findById(shopId);
        AtomicReference<ShopInfoDetailDto> shopDetailsResDto = new AtomicReference<>();

        AtomicReference<SingleResult<ShopInfoDetailDto>> singleResult = new AtomicReference<>();


        shopOptional.ifPresentOrElse(shop -> {

            shopDetailsResDto.set(ShopInfoDetailDto.builder()
                    .businessHours(shop.getBusinessHours())
                    .createShopDate(shop.getCreateShopDate())
                    .id(shop.getId())
                    .shopAddress(shop.getShopAddress())
                    .shopName(shop.getShopName())
                    .shopTelephone(shop.getShopTelephone())
                    .mainMenu(shop.getMainMenu())
                    .category(shop.getCategory())
                    .build());

            singleResult.set(responseService.getSingleResult((shopDetailsResDto.get() == null) ? new ShopInfoDetailDto() :
                    shopDetailsResDto.get()));


        }, () -> {
            singleResult.set(responseService.getfailResult(409, new ShopInfoDetailDto()));
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


        AtomicReference<SingleResult<ShopAllMenuDto>> singleResult = new AtomicReference<>();

        shopOptional.ifPresentOrElse(shop -> {

            Optional<List<Product>> allMenuByShopId = productRepository.findAllByShopId(shop.getId());

            allMenuByShopId.ifPresent( menu ->{

                IntStream.range(0, menu.size()).forEach(i -> {
                    Product product = menu.get(i);
                    shopDetailsResDto.getShopAllMenuList().add(new KeyValueDto<>(i, ProductInfoDto.builder()
                            .category(product.getCategory())
                            .createDate(product.getCreateDate())
                            .discountPrice(product.getDiscountPrice())
                            .likeCount(product.getLikeCount())
                            .endDate(product.getEndDate())
                            .id(product.getId())
                            .productName(product.getProductName())
                            .productPrice(product.getProductPrice())
                            .productContent(product.getProductContent())
                            .productImage(product.getProductImage())
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


    //검색결과 상점
    @ApiOperation(value = "shop/info/keyword", notes = "검색어를 통한 상점검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword", value ="검색어", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 409, message = "해당 지역구 결과 없음."),
    })
    @GetMapping("shop/info/keyword")
    public SingleResult<ShopBestByLocateResDto> searchShopByKeyword(@RequestParam("keyword") String keyword) {

        ShopBestByLocateResDto shopBestByLocateResDto = new ShopBestByLocateResDto(new ArrayList<>());

        if (keyword.equals("")) {
            keyword = "%";
        }else {
            keyword = "%" + keyword + "%";
        }

        List<Shop> allByShopNameLike = shopRepository.findAllByShopNameLike(keyword);
        List<Product> byProductName = productRepository.findByProductName(keyword);

        HashSet<Long> shopIdSet = new HashSet<>();

        int i=0;

        for (Shop shop : allByShopNameLike) {
            shopIdSet.add(shop.getId());
        }

        for (Product product : byProductName) {
            Shop shopByProduct = product.getShop();
            if (!shopIdSet.contains(shopByProduct.getId())) {
                shopIdSet.add(shopByProduct.getId());
                allByShopNameLike.add(shopByProduct);

            }
        }

        for (Shop shop : allByShopNameLike) {
            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .address(shop.getShopAddress())
                    .shopId(shop.getId())
                    .category(shop.getCategory())
                    .shopName(shop.getShopName())
                    .build();

            shopBestByLocateResDto.getTopShopByLocate().add(new KeyValueDto<>(i++, shopInfoDto));
        }

        if(allByShopNameLike==null||allByShopNameLike.size()==0){
            return  responseService.getSingleResult(shopBestByLocateResDto);
        }

        return responseService.getSingleResult(shopBestByLocateResDto);
    }


}
