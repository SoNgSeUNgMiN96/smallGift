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

    @ApiModelProperty(example = "11000")
    int discountPrice;

    @ApiModelProperty(example = "84")
    int discountRate;

    public void setDiscountRate(){
        discountRate = price/discountPrice;
    }
}
