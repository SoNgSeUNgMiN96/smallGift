package com.sgwannabig.smallgift.springboot.service;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.sgwannabig.smallgift.springboot.domain.User;
import com.sgwannabig.smallgift.springboot.dto.ProfileDto;
import com.sgwannabig.smallgift.springboot.repository.UserRepository;
import com.sgwannabig.smallgift.springboot.service.oauth.OAuthRequest;
import com.sgwannabig.smallgift.springboot.service.oauth.OAuthRequestFactory;
import com.sgwannabig.smallgift.springboot.service.oauth.profile.GoogleProfile;
import com.sgwannabig.smallgift.springboot.service.oauth.profile.KakaoProfile;
import com.sgwannabig.smallgift.springboot.service.oauth.profile.NaverProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.naming.CommunicationException;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
public class ProviderService {
    private final OAuthRequestFactory oAuthRequestFactory;
    private final UserRepository userRepository;
    public AccessToken getAccessToken(String code, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequest.getMap(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), AccessToken.class);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    public ProfileDto getProfile(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = oAuthRequestFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response, provider);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    private ProfileDto extractProfile(ResponseEntity<String> response, String provider) {
        if (provider.equals("kakao")) {
            KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
            return new ProfileDto(kakaoProfile.getKakao_account().getEmail());
        } else if(provider.equals("google")) {
            GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
            return new ProfileDto(googleProfile.getEmail());
        } else {
            NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
            return new ProfileDto(naverProfile.getResponse().getEmail());
        }
    }

    @Transactional
    public MemberLoginResponseDto loginMemberByProvider(String code, String provider) {
        AccessToken accessToken = providerService.getAccessToken(code, provider);
        ProfileDto profile = providerService.getProfile(accessToken.getAccess_token(), provider);

        Optional<User> findMember = userRepository.findByEmailAndProvider(profile.getEmail(), provider);
        if (findMember.isPresent()) {
            User member = findMember.get();
            member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
            return new MemberLoginResponseDto(member.getId(), jwtTokenProvider.createToken(findMember.get().getUsername()), member.getRefreshToken());
        } else {
            User saveMember = saveMember(profile, provider);
            saveMember.updateRefreshToken(jwtTokenProvider.createRefreshToken());
            return new MemberLoginResponseDto(saveMember.getId(), jwtTokenProvider.createToken(saveMember.getUsername()), saveMember.getRefreshToken());
        }
    }
}
