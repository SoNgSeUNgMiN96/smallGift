package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ManagerDto {
    @ApiModelProperty(value = "13")
    long managerId;

    @ApiModelProperty(example = "홍길동")
    String username;

    @ApiModelProperty(example = "홍짜장")
    String businessName;

    @ApiModelProperty(example = "경기도 안양시 만안구 XXX")
    String address;

    @ApiModelProperty(example = "999-99-000949")
    String businessTel;

    @ApiModelProperty(example = "음식점")
    String businessType;

    @ApiModelProperty(example = "우리은행")
    String bankName;

    @ApiModelProperty(example = "110-464-062460")
    String bankAccount;

    @ApiModelProperty(example = "/test/menu.png")
    String managerImage;
}

