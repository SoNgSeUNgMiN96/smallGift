package com.sgwannabig.smallgift.springboot.dto.signup;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSocialLoginResponseDto {

    @ApiModelProperty(example = "13")
    private Long id;
    @ApiModelProperty(example = "Bearer ~~")
    private String jwtAccessToken;
    @ApiModelProperty(example = "Bearer ~~")
    private String jwtRefreshToken;
}
