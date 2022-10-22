package com.sgwannabig.smallgift.springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgwannabig.smallgift.springboot.dto.login.KakaoProfile;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.OauthToken;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class UserService {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OauthToken getKakaoAccessToken(String code) {

        //(2)
        RestTemplate rt = new RestTemplate();

        //(3)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        System.out.println("code = "+code);

        //(4)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "a865c4442f6a8a5ad97d0b11c0d1e379");
        params.add("redirect_uri", "http://localhost:3000/auth/kakao/callback");
        params.add("code", code);
        //params.add("client_secret", "{시크릿 키}"); // 생략 가능!

        //(5)
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //(6)
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        System.out.println("현재 kakaoToken Response"+accessTokenResponse.toString());

        //(7)
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken; //(8)
    }


    public Member saveUser(String token) {

        //(1)
        KakaoProfile profile = findProfile(token);

        //(2)
        Member member = memberRepository.findByEmail(profile.getKakao_account().getEmail());

        //(3)
        if(member == null) {
            member = new Member();
            member.setUsername("kakao_"+profile.getId());
            member.setEmail(profile.getKakao_account().getEmail());
            member.setProvider("KAKAO");
            member.setRole("ROLE_USER");
            memberRepository.save(member);
        }

        return member;
    }

    //(1-1)
    public KakaoProfile findProfile(String token) {

        //(1-2)
        RestTemplate rt = new RestTemplate();

        //(1-3)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); //(1-4)
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //(1-5)
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        //(1-6)
        // Http 요청 (POST 방식) 후, response 변수에 응답을 받음
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        //(1-7)
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    public Member getNaverUserInfo(String accessToken){

        //사용자 정보 요청하기
        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        //결과값
        JSONObject response = webClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/v1/nid/me")
                        .build())
                .header("Authorization", "Bearer"+accessToken)
                .retrieve().bodyToMono(JSONObject.class).block();

        Map<String, Object> res = (Map<String, Object>) response.get("response");

        String id = (String) res.get("id");
        String email = (String) res.get("email");

        Member member = memberRepository.findByEmail(email);

        //(3)
        if(member == null) {
            member = new Member();
            member.setUsername("naver_"+id);
            member.setEmail(email);
            member.setProvider("NAVER");
            member.setRole("ROLE_USER");
            memberRepository.save(member);
        }
        return member;
    }


}