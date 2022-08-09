package com.sgwannabig.smallgift.springboot.dto.settlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SettlementResponseDto {
    @ApiModelProperty(value = "3")
    private long listNum;

    @ApiModelProperty(value = "20221017")
    private String depositDate;

    @ApiModelProperty(value = "125000")
    private int depositPrice;

    @ApiModelProperty(value = "20220914")
    private String settlement_start_dt;

    @ApiModelProperty(value = "20221114")
    private String settlement_end_dt;
}
