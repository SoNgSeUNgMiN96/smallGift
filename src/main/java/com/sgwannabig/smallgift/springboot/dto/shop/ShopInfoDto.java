package com.sgwannabig.smallgift.springboot.dto.shop;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopInfoDto {


    @ApiModelProperty(example = "카페")
    String category;

    @ApiModelProperty(example = "을지다락 강남")
    String shopName;

    @ApiModelProperty(example = "서울 강남구 강남대로9실 22 2층")
    String address;

    @ApiModelProperty(example = "4")
    long shopId;
}
