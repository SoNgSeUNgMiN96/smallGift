package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ManagerResponseDto {
    @ApiModelProperty(value = "200")
    private String code;
    @ApiModelProperty(value = "사업자가 정상적으로 등록되었습니다.")
    private String message;
    @ApiModelProperty(value = "4")
    private long managerId;
}
