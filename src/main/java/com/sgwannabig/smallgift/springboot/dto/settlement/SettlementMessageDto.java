package com.sgwannabig.smallgift.springboot.dto.settlement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SettlementMessageDto {
    @ApiModelProperty(value = "200")
    private String code;
    @ApiModelProperty(value = "정산 처리가 정상적으로 완료되었습니다.")
    private String message;
    @ApiModelProperty(value = "17")
    private long managerId;
}
