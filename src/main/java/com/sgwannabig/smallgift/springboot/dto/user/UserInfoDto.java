package com.sgwannabig.smallgift.springboot.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    @ApiModelProperty(example = "21")
    long memberId;

    @ApiModelProperty(example = "010-2248-1245")
    String userPhone;

    @ApiModelProperty(example = "이모상")
    String userName;

    @ApiModelProperty(example = "국민은행")
    String accountBank;

    @ApiModelProperty(example = "12500-2043-43372")
    String accountNumber;
}
