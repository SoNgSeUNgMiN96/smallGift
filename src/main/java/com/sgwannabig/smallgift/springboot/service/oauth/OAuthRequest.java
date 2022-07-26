package com.sgwannabig.smallgift.springboot.service.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.util.LinkedMultiValueMap;

@Getter
@AllArgsConstructor
public class OAuthRequest {
    private String url;
    private LinkedMultiValueMap<String, String> map;
}
