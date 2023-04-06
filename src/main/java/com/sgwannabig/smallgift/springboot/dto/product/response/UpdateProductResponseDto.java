package com.sgwannabig.smallgift.springboot.dto.product.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateProductResponseDto {
    @ApiModelProperty(value = "15")
    long productId;

    @ApiModelProperty(value = "육회비빔밥")
    String productName;

    @ApiModelProperty(value = "8900")
    int productPrice;

    @ApiModelProperty(value = "17")
    long productStock;

    @ApiModelProperty(value = "20220907")
    String start_dt;

    @ApiModelProperty(value = "20230904")
    String end_dt;
}
