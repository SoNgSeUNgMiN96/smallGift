package com.sgwannabig.smallgift.springboot.dto.shop;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopMenuDto {

    @ApiModelProperty(example = "23")
    long productId;

    @ApiModelProperty(example = "A세트")
    String productTitle;

    @ApiModelProperty(example = "아메리카노 + 조각케익")
    String productDetails;

    @ApiModelProperty(example = "13000")
    int price;
}
