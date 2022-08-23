package com.sgwannabig.smallgift.springboot.dto.settlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesInfo {
    @ApiModelProperty(value = "2022/07/18")
    private String salesDate;

    @ApiModelProperty(value = "베이비 아포가토")
    private String productName;

    @ApiModelProperty(value = "4900")
    private int productPrice;

    @ApiModelProperty(value = "김은지")
    private String productBuyer;
}
