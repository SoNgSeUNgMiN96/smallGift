package com.sgwannabig.smallgift.springboot.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequestDto {
    @ApiModelProperty(value = "아이디", example = "test1234", dataType = "String")
    private String username;
    @ApiModelProperty(value = "비밀번호", example = "test1234!", dataType = "String")
    private String password;
}
