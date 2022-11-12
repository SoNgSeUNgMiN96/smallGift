package com.sgwannabig.smallgift.springboot.dto.login;


import com.sgwannabig.smallgift.springboot.domain.Provider;
import com.sgwannabig.smallgift.springboot.domain.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignupDto {

    @ApiModelProperty(value = "아이디", example = "tmd9544", dataType = "string", notes = "사용자 아이디를 입력합니다.")
    private String username;
    @ApiModelProperty(value = "비밀번호", example = "test1234!", dataType = "String")
    private String password;
    @ApiModelProperty(value = "email", example = "test1234@naver.com", dataType = "String")
    private String email;
    @ApiModelProperty(value = "유저/매니저", example = "ROLE_USER", dataType = "enum")
    private Role Role;
}