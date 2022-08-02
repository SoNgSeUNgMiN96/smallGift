package com.sgwannabig.smallgift.springboot.dto.shop;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopInfoDto {

    @ApiModelProperty(example = "서울시 강남구")
    String local;

    @ApiModelProperty(example = "카페")
    String category;

    @ApiModelProperty(example = "을지다락 강남")
    String restaurantName;

    @ApiModelProperty(example = "서울 강남구 강남대로9실 22 2층")
    String address;

    @ApiModelProperty(example = "4")
    long shopId;
}
