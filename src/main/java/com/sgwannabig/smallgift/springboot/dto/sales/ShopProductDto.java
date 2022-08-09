package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopProductDto {
    @ApiModelProperty(value = "1")
    private long listNum;

    @ApiModelProperty(value = "3")
    private long productId;

    @ApiModelProperty(value = "베이비 아포가토")
    private String productName;

    @ApiModelProperty(value = "4900")
    private int productPrice;

    @ApiModelProperty(value = "1")
    private int status;

}
