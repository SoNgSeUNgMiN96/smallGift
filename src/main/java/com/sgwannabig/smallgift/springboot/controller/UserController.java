package com.sgwannabig.smallgift.springboot.controller;


import com.sgwannabig.smallgift.springboot.domain.Member;
import com.sgwannabig.smallgift.springboot.domain.User;
import com.sgwannabig.smallgift.springboot.dto.user.UserLocateDto;
import com.sgwannabig.smallgift.springboot.dto.user.UserInfoDto;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.repository.UserRepository;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Component
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final ResponseService responseService;


    @ApiOperation(value = "/locate", notes = "유저의 좌표를 받아옵니다. <- Get임. 헷갈리지 않기")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value ="유저(멤버) 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 408, message = "유저 ID에 매치되는 userInfo가 없습니다. 기본주소를 사용해주세요."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/locate")
    public ResponseEntity<String> getUserLocate(@RequestParam long userId){

        Optional<Member> member = memberRepository.findById(userId);

        if(!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userId를 찾기 못했습니다");
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(String.valueOf(userId));
        User user;

        if(orinUser!=null){       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        }else{      //없다면 새로운 유저 생성
            return ResponseEntity.status(409).body("userId에 해당하는 주소가 아직 없습니다. 기본주소로 사용해주세요.");
        }

        return ResponseEntity.ok(user.getUserArea());
    }

    @ApiOperation(value = "/locate", notes = "유저의 좌표를 잡아줍니다. <- Post임 헷갈리지 않기")
    @ApiImplicitParams({
            @ApiImplicitParam(name="locate", value ="유저 설정 주소", required = true),
            @ApiImplicitParam(name="userId", value ="유저(멤버) 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/locate")
    public ResponseEntity<String> setUserLocate(@RequestBody UserLocateDto userLocateDto){

        Optional<Member> member = memberRepository.findById(userLocateDto.getUserId());

        if(!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userId를 찾기 못했습니다");
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(String.valueOf(userLocateDto.getUserId()));
        User user;

        if(orinUser!=null){       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        }else{      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
        }

        user.setUserArea(user.getUserArea());
        userRepository.save(user);

        return ResponseEntity.ok("success");
    }


    @ApiOperation(value = "/userInfo", notes = "유저의 추가정보를 입력합니다. (수정된게 하나여도 마이페이지 기준으로 다 넘겨줘야함")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value ="유저(맴버) 아이디 (서버에서 보내준 DB상 memberID)", required = true),
            @ApiImplicitParam(name="userPhone", value ="유저 휴대폰 번호", required = true),
            @ApiImplicitParam(name="userPolicy", value ="이용약관동의여부", required = true),
            @ApiImplicitParam(name="userInfoAgree", value ="개인정보 취급 동의여부", required = true),
            @ApiImplicitParam(name="userLocationAgree", value ="위치기반서비스동의여부", required = true),
            @ApiImplicitParam(name="userPhone", value ="유저 휴대폰 번호", required = true),
            @ApiImplicitParam(name="accountBank", value ="환불 계좌은행", required = true),
            @ApiImplicitParam(name="accountNumber", value ="환불 계좌 번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "userId 없음"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/userInfo")
    public ResponseEntity<String> setUserInfo(@RequestBody UserInfoDto userInfoDto){

        Optional<Member> member = memberRepository.findById(userInfoDto.getUserId());

        if(!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 유저를 생성하면 안됨.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userId를 찾기 못했습니다");
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(String.valueOf(userInfoDto.getUserId()));
        User user;

        if(orinUser!=null){       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        }else{      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
        }


        //모든 정보대로 설정
        user.setUserPhone(userInfoDto.getUserPhone());
        user.setUserRefundBank(userInfoDto.getAccountBank());
        user.setUserRefundAccount(userInfoDto.getAccountNumber());
        user.setUserLocationAgree(userInfoDto.isUserLocationAgree());
        user.setUserInfoAgree(userInfoDto.isUserInfoAgree());
        user.setUserPolicyAgree(userInfoDto.isUserPolicyAgree());

        userRepository.save(user);

        return ResponseEntity.ok("success");
    }

    @ApiOperation(value = "/userInfo", notes = "유저의 추가정보를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value ="유저(맴버) 아이디 (서버에서 보내준 DB상 memberID)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/userInfo")
    public SingleResult<UserInfoDto> getUserInfo(@RequestParam long userId){

        Optional<Member> member = memberRepository.findById(userId);

        if(!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러

            SingleResult fail = new SingleResult();
            fail.setCode(409);
            fail.setMsg("유저 ID 가 없습니다.");
            return fail;
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(String.valueOf(userId));
        User user;

        if(orinUser!=null){       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        }else{      //없음

            SingleResult fail = new SingleResult();
            fail.setCode(408);
            fail.setMsg("유저 ID에 해당하는 userInfo가 없습니다.");
            return fail;
        }

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(user.getId());
        userInfoDto.setUserPhone(user.getUserPhone());
        userInfoDto.setAccountBank(user.getUserRefundBank());
        userInfoDto.setAccountNumber(user.getUserRefundAccount());
        userInfoDto.setUserInfoAgree(user.isUserInfoAgree());
        userInfoDto.setUserLocationAgree(user.isUserLocationAgree());
        userInfoDto.setUserPolicyAgree(user.isUserPolicyAgree());


        return responseService.getSingleResult(userInfoDto);

    }



}
