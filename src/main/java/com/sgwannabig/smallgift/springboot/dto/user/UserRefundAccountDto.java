package com.sgwannabig.smallgift.springboot.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRefundAccountDto {

    @ApiModelProperty(example = "21")
    long userId;

    @ApiModelProperty(example = "국민은행")
    String accountBank;

    @ApiModelProperty(example = "12500-2043-43372")
    String accountNumber;
}
