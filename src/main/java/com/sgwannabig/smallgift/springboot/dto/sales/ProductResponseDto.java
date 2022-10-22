package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductResponseDto {
    @ApiModelProperty(value = "200")
    private String code;
    @ApiModelProperty(value = "상품이 등록되었습니다.")
    private String message;
    @ApiModelProperty(value = "13")
    private long productId;
}
