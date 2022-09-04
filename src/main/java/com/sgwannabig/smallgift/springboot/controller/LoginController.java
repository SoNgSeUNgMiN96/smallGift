package com.sgwannabig.smallgift.springboot.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgwannabig.smallgift.springboot.config.auth.PrincipalDetails;
import com.sgwannabig.smallgift.springboot.config.jwt.JwtProperties;
import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.OauthToken;
import com.sgwannabig.smallgift.springboot.domain.RefreshToken;
import com.sgwannabig.smallgift.springboot.dto.login.AccessTokenDto;
import com.sgwannabig.smallgift.springboot.dto.login.JwtDto;
import com.sgwannabig.smallgift.springboot.dto.login.SignupDto;
import com.sgwannabig.smallgift.springboot.dto.login.SignupResponseDto;
import com.sgwannabig.smallgift.springboot.dto.signup.MemberLoginRequestDto;
import com.sgwannabig.smallgift.springboot.dto.signup.MemberLoginResponseDto;
import com.sgwannabig.smallgift.springboot.dto.signup.MemberSocialLoginResponseDto;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.repository.RefreshTokenRepository;
import com.sgwannabig.smallgift.springboot.service.MemberService;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.UserService;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResponseService responseService;
    private final MemberService memberService;

    @Autowired
    private UserService userService;

    PasswordEncoder passwordEncoder;
    ObjectMapper om = new ObjectMapper();

    @Autowired
    public LoginController(MemberRepository memberRepository, RefreshTokenRepository refreshTokenRepository,
                           PasswordEncoder passwordEncoder, ResponseService responseService, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.responseService = responseService;
        this.memberService = memberService;
    }



    @ApiOperation(value = "oauth/kakao/token", notes = "kakao login API입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="code", value ="카카오에서 발급받은 코드", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/oauth/kakao/token")
    public SingleResult<MemberSocialLoginResponseDto> getKakaoLogin(@RequestParam("code") String code) throws Exception{

        // 넘어온 인가 코드를 통해 access_token 발급
        OauthToken oauthToken = userService.getKakaoAccessToken(code);
        //(1)
        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        Member member = userService.saveUser(oauthToken.getAccess_token());

        String jwtAccessToken = JWT.create()
                .withSubject(member.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", member.getId())
                .withClaim("username", member.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        String jwtRefreshToken = JWT.create()
                .withSubject(member.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))  //14일로 추가.
                .withClaim("id", member.getId())
                .withClaim("username", member.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));


        refreshTokenRepository.save(new RefreshToken(jwtRefreshToken));

        MemberSocialLoginResponseDto memberSocialLoginResponseDto = new MemberSocialLoginResponseDto(member.getId(), JwtProperties.TOKEN_PREFIX + jwtAccessToken, JwtProperties.TOKEN_PREFIX + jwtRefreshToken);

        return responseService.getSingleResult(memberSocialLoginResponseDto);
    }


    @ApiOperation(value = "oauth/naver/token", notes = "naver login API입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="code", value ="네이버 발급받은 코드", required = true),
            @ApiImplicitParam(name="state", value ="네이버 에 보낸 state", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/oauth/naver/token")
    public SingleResult<MemberSocialLoginResponseDto> getNaverLogin(@RequestParam("code") String code) throws Exception{

        // 넘어온 인가 코드를 통해 access_token 발급
        WebClient webClient = WebClient.builder()
                .baseUrl("https://nid.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


        JSONObject response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2.0/token")
                        //비밀번호 변수화하기
                        //네이버는 잠정 연기입니다.
                        .queryParam("client_id","")
                        .queryParam("client_secret","")
                        .queryParam("grant_type","authorization_code")
                        .queryParam("state","abc")
                        .queryParam("code",code)
                        .build())
                .retrieve().bodyToMono(JSONObject.class).block();

        String token = (String) response.get("access_token");

        //(1)
        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        Member member = userService.getNaverUserInfo(token);

        String jwtAccessToken = JWT.create()
                .withSubject(member.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", member.getId())
                .withClaim("username", member.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        String jwtRefreshToken = JWT.create()
                .withSubject(member.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))  //14일로 추가.
                .withClaim("id", member.getId())
                .withClaim("username", member.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        refreshTokenRepository.save(new RefreshToken(jwtRefreshToken));

        MemberSocialLoginResponseDto memberSocialLoginResponseDto = new MemberSocialLoginResponseDto(member.getId(), JwtProperties.TOKEN_PREFIX + jwtAccessToken, JwtProperties.TOKEN_PREFIX + jwtRefreshToken);

        return responseService.getSingleResult(memberSocialLoginResponseDto);
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
    @GetMapping("")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());
        return "<h1>user  user</h1>";
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
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요."),
            @ApiResponse(code = 406, message = "이미 가입된 이메일입니다.")
    })
    @PostMapping("signup")
    public SingleResult<SignupResponseDto> signup(@RequestBody SignupDto signupDto, HttpServletResponse response) {

        if(signupDto==null){
            response.setStatus(405);
            return null;
            //return "값이 없습니다.";
        }

        Optional<Member> isUser = Optional.ofNullable(memberRepository.findByUsername(signupDto.getUsername()));

        Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher = passPattern.matcher(signupDto.getPassword());

        Pattern emailPattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
        Matcher emailMatcher = emailPattern.matcher(signupDto.getEmail());

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

        isUser = Optional.ofNullable(memberRepository.findByEmail(signupDto.getEmail()));


        if(isUser.isPresent()){
            response.setStatus(406);
            return null;
            //return "이미 존재하는 이메일입니다.";
        }

        Member member = new Member();
        member.setUsername(signupDto.getUsername());
        member.setPassword(signupDto.getPassword());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        //없는지 검사하는것도 필요함
        System.out.println(signupDto);
        member.setRole(signupDto.getRole().name());
        member.setEmail(signupDto.getEmail());
        member.setProvider(signupDto.getProvider().name());

        memberRepository.save(member);

        SignupResponseDto signupSignupResponseDto = new SignupResponseDto();
        signupSignupResponseDto.setId(member.getId());
        signupSignupResponseDto.setUsername(member.getUsername());

        return responseService.getSingleResult(signupSignupResponseDto);
    }

    @ApiOperation(value = "로컬 로그인", notes = "로컬을 통해 로그인을 진행한다.") // 구현 O
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "아이디", required = true),
            @ApiImplicitParam(name = "password", value = "패스워드", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 402, message = "비밀번호는영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @GetMapping("/login")
    public SingleResult<MemberLoginResponseDto> loginMember(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = memberService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "아이디 중복체크", notes = "아이디 중복 확인을 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "사용자 아이디", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "아이디 중복 확인 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "중복된 아이디 입니다.")
    })
    @PostMapping("/{username}/exists")
    public String checkIdDuplicate(@PathVariable String username) {
        if (memberService.checkUsernameDuplicate(username)) {
            return "이미 사용중인 아이디입니다.";
        } else {
            return "사용할 수 있는 아이디입니다.";
        }
        //return ResponseEntity.ok(managerService.checkUsernameDuplicate(username));
    }


    @ApiOperation(value = "이메일 중복체크", notes = "이메일 중복 확인을 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "사용자 이메일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "이메일 중복 확인 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "중복된 아이디 입니다.")
    })
    @PostMapping("/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) throws Exception{
        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기를 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "사용자 이메일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "아이디 찾기 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "올바르지 않은 이메일 형식입니다.")
    })
    @PostMapping("/find/id")
    public ResponseEntity<String> findId(@RequestBody String email) {
        String username = memberService.findId(email);
        if(username == null) return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디를 찾지 못했습니다.");
        return ResponseEntity.ok(username);
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기를 진행한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 아이디", required = true),
            @ApiImplicitParam(name = "email", value = "사용자 이메일", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "비밀번호 찾기 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "이메일 형식이 올바르지 않습니다.")
    })
    @PostMapping("/find/password")
    public String findPwd(@RequestBody MemberLoginRequestDto memberLoginRequestDto, RedirectAttributes ra) {
        // 비밀번호를 찾으면 로그인 창으로 이동해 "임시 비밀번호를 이메일로 전송했습니다."라고 출력하려고 한다.
        // 비밀번호를 못 찾으면 GET /find/password로 이동해서 "비밀번호를 찾지 못했습니다"라고 출력하려고 한다.
        Boolean result = memberService.resetPassword(memberLoginRequestDto.getUsername(), memberLoginRequestDto.getEmail());
        if(result == true) {
            ra.addFlashAttribute("msg", "임시 비밀번호를 이메일로 전송했습니다.");
            return "임시 비밀번호를 이메일로 전송했습니다.";
        } else {
            ra.addFlashAttribute("msg", "비밀번호를 찾지 못했습니다.");
            return "비밀번호를 찾지 못했습니다.";
        }
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 변경한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 아이디", required = true),
            @ApiImplicitParam(name = "password", value = "사용자 패스워드", required = true),
            @ApiImplicitParam(name = "newPassword", value = "새로운 비밀번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "비밀번호 변경 완료"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 402, message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    })
    @PostMapping("/change")
    public String changePassword(@RequestBody MemberLoginRequestDto memberLoginRequestDto, @RequestParam("password") String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member findMember = memberService.findMemberByUsername(memberLoginRequestDto.getUsername());
        findMember.setPassword(newPassword);
        memberService.changePassword(findMember.getUsername(), passwordEncoder.encode(newPassword));
        return "비밀번호 변경 완료";
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴를 진행한다.") // 구현 O
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "회원 탈퇴에 실패했습니다."),
    })
    @DeleteMapping("/delete/{memberId}")
    public String resign(@PathVariable Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(selectMember -> {
            memberRepository.delete(selectMember);
        });
        if(member != null) return "회원탈퇴 성공";
        else return "회원탈퇴에 실패하였습니다.";
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
            if(e.getMessage().contains("expired")) {
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
}

