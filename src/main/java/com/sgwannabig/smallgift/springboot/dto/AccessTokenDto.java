package com.sgwannabig.smallgift.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDto {
    private String accessToken;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private long refresh_token_expires_in;
}
