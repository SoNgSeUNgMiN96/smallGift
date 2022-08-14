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

    @ApiModelProperty(example = "true")
    boolean userPolicyAgree;

    @ApiModelProperty(example = "true")
    boolean userInfoAgree;

    @ApiModelProperty(example = "true")
    boolean userLocationAgree;

    @ApiModelProperty(example = "국민은행")
    String accountBank;

    @ApiModelProperty(example = "12500-2043-43372")
    String accountNumber;
}
