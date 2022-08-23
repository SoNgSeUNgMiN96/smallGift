package com.sgwannabig.smallgift.springboot.dto.settlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesStatusResponseDto {

    @ApiModelProperty(value = "132")
    private int salesCount;

    @ApiModelProperty(value = "132500")
    private Long salesPrice;

    @ApiModelProperty(value = "32000")
    private Long discountPrice;

    @ApiModelProperty(value = "130450000")
    private Long settlementPrice;

    private List<SalesInfo> salesList;
}
