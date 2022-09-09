package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.domain.User;
import com.sgwannabig.smallgift.springboot.domain.WishList;
import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import com.sgwannabig.smallgift.springboot.dto.user.WishProductDto;
import com.sgwannabig.smallgift.springboot.dto.user.WishListReqDto;
import com.sgwannabig.smallgift.springboot.dto.user.WishListResDto;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.repository.UserRepository;
import com.sgwannabig.smallgift.springboot.repository.WishListRepository;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class WishListContoller {

    @Autowired
    private final ResponseService responseService;

    @Autowired
    private final WishListRepository wishListRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserRepository userRepository;


    @ApiOperation(value = "/wishList", notes = "유저의 찜 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "해당 유저를 기준으로 찜 목록 조회", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/wishList")
    public SingleResult<WishListResDto> getWishList(@RequestParam long userId) {

        Optional<List<WishList>> wishListByUser = wishListRepository.findByUserId(userId);

        WishListResDto wishListResDto = new WishListResDto(new ArrayList<>());

        AtomicInteger idx= new AtomicInteger(0);

        AtomicReference<SingleResult<WishListResDto>> singleResult = null;

        wishListByUser.ifPresent( wishList -> {

            wishList.stream().forEach(wish->{
                Product product = wish.getProduct();

                if(product!=null) {

                    wishListResDto.getWishList().add(new KeyValueDto<Integer, WishProductDto>(idx.incrementAndGet(), WishProductDto.builder()
                            .wishListId(wish.getId())
                            .discountPrice(product.getDiscountPrice())
                            .productName(product.getProductName())
                            .category(product.getCategory())
                            .productStock(product.getProductStock())
                            .productPrice(product.getProductPrice())
                            .productId(product.getId())
                            .build()));
                }
            });

        });

        singleResult.set(responseService.getSingleResult(wishListResDto));
        return singleResult.get();
    }


    @ApiOperation(value = "/wishList", notes = "유저의 찜 목록을 추가합니다.")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userId", value = "해당 유저를 기준으로 찜 생성", required = true),
            @ApiImplicitParam(name = "productId", value = "해당 상품을 기준으로 찜 생성", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "이미 찜한 상품입니다."),
            @ApiResponse(code = 410, message = "해당하는 유저 또는 상품이 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/wishList")
    public SingleResult<String> insertWishList(@RequestBody WishListReqDto wishListReqDto) {

        boolean isExist = wishListRepository.existsByUserIdAndProductId(wishListReqDto.getUserId(), wishListReqDto.getProductId());

        Optional<Product> productById = productRepository.findById(wishListReqDto.getProductId());
        Optional<User> userById = userRepository.findById(wishListReqDto.getUserId());

        SingleResult singleResult = new SingleResult();

        if(isExist){

            singleResult.setCode(409);
            singleResult.setMsg("이미 찜이 되어있는 상품입니다.");
            return singleResult;
        }

        if(productById.isEmpty()||userById.isEmpty()){
            singleResult.setCode(409);
            singleResult.setMsg("해당하는 유저 또는 상품이 없습니다");
            return singleResult;
        }

        WishList wishList = new WishList();
        wishList.setProduct(productById.get());
        wishList.setUser(userById.get());

        wishListRepository.save(wishList);

        return responseService.getSingleResult("찜 성공");
    }


    @ApiOperation(value = "/wishList", notes = "유저의 찜 목록을 추가합니다.")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "wishListId", value = "해당 찜 삭제", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "해당 찜 Id가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @DeleteMapping("/wishList")
    public SingleResult<String> deleteWishList(@RequestBody long wishListId) {


        Optional<WishList> wishListById = wishListRepository.findById(wishListId);

        AtomicReference<SingleResult<String>> singleResult = null;

        wishListById.ifPresentOrElse( wishList -> {
            wishListRepository.delete(wishList);
            singleResult.set(responseService.getSingleResult("삭제 성공"));
        },()->{
            singleResult.set(responseService.getfailResult(409,"해당 찜이 없습니다."));
        });

        return singleResult.get();
    }



}
