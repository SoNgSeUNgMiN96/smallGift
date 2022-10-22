package com.sgwannabig.smallgift.springboot.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
public class AccessTokenDto {
    private String accessToken;

    @Builder
    public AccessTokenDto(String accessToken){
        this.accessToken = accessToken;
    }
}
