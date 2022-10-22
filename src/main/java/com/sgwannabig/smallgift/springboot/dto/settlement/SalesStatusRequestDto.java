package com.sgwannabig.smallgift.springboot.dto.settlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesStatusRequestDto {

    @ApiModelProperty(value = "2022/10/17")
    private String startDate;

    @ApiModelProperty(value = "2022/10/24")
    private String endDate;
}
