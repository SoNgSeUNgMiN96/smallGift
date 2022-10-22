package com.sgwannabig.smallgift.springboot.dto.login;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtDto {       //로그인시, 엑세스와 리프레시 토큰 발급.

    String jwtAccessToken;
    String jwtRefreshToken;

    @Builder
    JwtDto(String jwtAccessToken, String jwtRefreshToken){
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }
}
