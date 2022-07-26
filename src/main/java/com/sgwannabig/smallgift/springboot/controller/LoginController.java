package com.sgwannabig.smallgift.springboot.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgwannabig.smallgift.springboot.config.auth.PrincipalDetails;
import com.sgwannabig.smallgift.springboot.config.jwt.JwtProperties;
import com.sgwannabig.smallgift.springboot.domain.RefreshToken;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.Role;
import com.sgwannabig.smallgift.springboot.dto.AccessTokenDto;
import com.sgwannabig.smallgift.springboot.dto.JwtDto;
import com.sgwannabig.smallgift.springboot.dto.SignupDto;
import com.sgwannabig.smallgift.springboot.dto.SignupResponseDto;
import com.sgwannabig.smallgift.springboot.repository.RefreshTokenRepository;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    PasswordEncoder passwordEncoder;
    ObjectMapper om = new ObjectMapper();

    @Autowired
    public LoginController(MemberRepository memberRepository, RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // 모든 사람이 접근 가능
    @ApiOperation(value = "HTTP GET EXAMPLE", notes = "GET 요청에 대한 예제 입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음")
    })

    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }


    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.


    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());
        return "<h1>user  user</h1>";
    }



    @ApiOperation(value = "login", notes = "login API입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="사용자 ID(email)", required = true),
            @ApiImplicitParam(name="password", value ="비밀번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "토큰 시간이 만료됨."),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @PostMapping("/login")
    public String login(@RequestBody HttpServletRequest request){
        return "스웨거 명세를 위한 api";
    }



    @ApiOperation(value = "reissueAccessToken", notes = "reissueAccessToken API입니다.\n" +
            "header에  key : token value : {\"jwtAccessToken\": \"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZXVuZ21pbjIiLCJpZCI6NCwiZXhwIjoxNjU2NTI0NjE2LCJ1c2VybmFtZSI6InNldW5nbWluMiJ9.ciIX0cXD3ahJIvB4f2GE60n0qRPaE2HQfwqr7nBtBoKiXKCSacaZh-2wKJo_9gXK9KtKUUAtIRh6vtfe0AapuQ\",\n \"jwtRefreshToken\":\"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZXVuZ21pbjIiLCJpZCI6NCwiZXhwIjoxNjU3NzM0MjA2LCJ1c2VybmF\ntZSI6InNldW5nbWluMiJ9.7K5zMxSlaUb1flbKCLYfuY83QUxnIF5LpjxJSuwKtBwXfvP2z6eN9_dmv3YUuDzEnRJFVT_moXjpagSG39oSiw\"} ")
    @ApiImplicitParams({
            @ApiImplicitParam(name="jwtAccessToken", value ="만료된 AccessToken", required = true),
            @ApiImplicitParam(name="jwtRefreshToken", value ="RefreshToken", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Access token reissued"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "Access token not Expire")
    })
    @GetMapping(path = "reissueAccessToken")
    public AccessTokenDto reissueAccessToken(@RequestHeader Map<String, Object> requestHeader, HttpServletResponse response) throws JsonProcessingException {

        //여기에 토큰을 받아옴. token : {"jwtAccessToken": "tekslkj", "jwtRefreshToken":"dfjwfwccc"}

        JwtDto jwtDto = om.readValue((String)requestHeader.get("token"), JwtDto.class);
        System.out.println(jwtDto.toString());
        String username;

        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtDto.getJwtAccessToken().replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getClaim("username").asString();

            System.out.println(username);

        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("expired")) {    //만료가 되었으니 재발급.
                RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(   jwtDto.getJwtRefreshToken().replace(JwtProperties.TOKEN_PREFIX, ""))  ;
                System.out.println(refreshToken.getRefreshToken()+"  "+refreshToken.getCreateDate());

                username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refreshToken.getRefreshToken())
                        .getClaim("username").asString();

                Member member = memberRepository.findByUsername(username);

                String jwtAccessToken = JWT.create()
                        .withSubject(member.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                        .withClaim("id", member.getId())
                        .withClaim("username", member.getUsername())
                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));

                AccessTokenDto accessTokenDto = new AccessTokenDto(JwtProperties.TOKEN_PREFIX+jwtAccessToken);

                //String tokensJson = om.writeValueAsString(accessTokenDto);
                //response.addHeader(JwtProperties.REFRESH_HEADER_STRING, tokensJson);
                return accessTokenDto;
            }
        }
        response.setStatus(401);
        return null;
        //return "Access token not Expire";
    }

    @ApiOperation(value = "signup", notes = "signup API입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value ="사용자 ID", required = true),
            @ApiImplicitParam(name="password", value ="비밀번호", required = true),
            @ApiImplicitParam(name="email", value ="사용자 Email", required = true),
            @ApiImplicitParam(name="Provider", value ="회원가입 유형(NORMAL, KAKAO, NAVER)", required = true),
            @ApiImplicitParam(name="Role", value ="권한(ROLE_USER, ROLE_MANAGER)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\n" +
                    "    \"id\": 34,\n" +
                    "    \"username\": \"testId5@naver.com\"\n" +
                    "}"),
            @ApiResponse(code = 201, message = "존재하지 않는 태그이름을 포함합니다. 해당 태그를 제외하고 회원가입이 성공하였습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 회원"),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다."),
            @ApiResponse(code = 403, message = "이메일 형식을 유지해주세요."),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("signup")
    public SignupResponseDto signup(@RequestBody SignupDto signupDto, HttpServletResponse response) {

        if(signupDto==null){
            response.setStatus(405);
            return null;
            //return "값이 없습니다.";
        }

        Optional<Member> isUser = Optional.ofNullable(memberRepository.findByUsername(signupDto.getUsername()));

        Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher = passPattern.matcher(signupDto.getPassword());

        Pattern emailPattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
        Matcher emailMatcher = emailPattern.matcher(signupDto.getUsername());

        if(isUser.isPresent()){
            response.setStatus(401);
            return null;
            //return "이미 존재하는 회원명입니다.";
        }

        if(!passMatcher.find()){
            response.setStatus(402);
            return null;
            //return "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.";
        }

        if(!emailMatcher.find()){
            response.setStatus(403);
            return null;
            //return "이메일 형식을 유지해주세요.";
        }

        Member member = new Member();
        member.setUsername(signupDto.getUsername());
        member.setPassword(signupDto.getPassword());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        //없는지 검사하는것도 필요함
        System.out.println(signupDto);
        member.setRole(signupDto.getRole());
        member.setEmail(signupDto.getEmail());

        memberRepository.save(member);

        SignupResponseDto signupSignupResponseDto = new SignupResponseDto();
        signupSignupResponseDto.setId(member.getId());
        signupSignupResponseDto.setUsername(member.getUsername());
        return signupSignupResponseDto;
    }
}
