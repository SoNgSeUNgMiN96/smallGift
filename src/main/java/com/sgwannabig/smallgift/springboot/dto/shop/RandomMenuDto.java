package com.sgwannabig.smallgift.springboot.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RandomMenuDto {
    //가게이름, 상품이름, 가격, 할인가, 할인율
    @ApiModelProperty(example = "카멜로연남")
    String shopName;
    @ApiModelProperty(example = "버터 봉골레 파스타")
    String productName;
    @ApiModelProperty(example = "17000")
    int price;
    @ApiModelProperty(example = "15300")
    int discountPrice;

    @ApiModelProperty(example = "http://sdlkfjwlkejf.sdlkjf")
    String image;
    @ApiModelProperty(example = "10")
    double discountRate;
}