package com.sgwannabig.smallgift.springboot.controller.user;


import com.sgwannabig.smallgift.springboot.domain.*;
import com.sgwannabig.smallgift.springboot.dto.KeyValueDto;
import com.sgwannabig.smallgift.springboot.dto.user.*;
import com.sgwannabig.smallgift.springboot.repository.AllKeywordRepository;
import com.sgwannabig.smallgift.springboot.repository.MemberRepository;
import com.sgwannabig.smallgift.springboot.repository.UserKeywordRepository;
import com.sgwannabig.smallgift.springboot.repository.UserRepository;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.MultipleResult;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    @Autowired
    private final UserKeywordRepository userKeywordRepository;

    @Autowired
    private final AllKeywordRepository allKeywordRepository;


    @ApiOperation(value = "/locate", notes = "유저의 좌표를 받아옵니다. <- Get임. 헷갈리지 않기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            //@ApiResponse(code = 408, message = "유저 ID에 매치되는 userInfo가 없습니다. 기본주소를 사용해주세요."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/locate")
    public ResponseEntity<String> getUserLocate(@RequestParam long memberId) {


        Optional<Member> member = memberRepository.findById(memberId);

        if (!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userId를 찾기 못했습니다");
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로직 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(memberId);
        User user;

        if (orinUser != null) {       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        } else {      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
            user.setUserArea("서울시 강남구");
            userRepository.save(user);      //user 저장
            //return ResponseEntity.status(409).body("userId에 해당하는 주소가 아직 없습니다. 기본주소로 사용해주세요.");
        }

        return ResponseEntity.ok(user.getUserArea());
    }

    @ApiOperation(value = "/locate", notes = "유저의 좌표를 잡아줍니다. <- Post임 헷갈리지 않기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locate", value = "유저 설정 주소", required = true),
            @ApiImplicitParam(name = "memberId", value = "멤버 아이디", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/locate")
    public ResponseEntity<String> setUserLocate(@RequestBody UserLocateDto userLocateDto) {

        Optional<Member> member = memberRepository.findById(userLocateDto.getMemberId());

        if (!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userId를 찾기 못했습니다");
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(userLocateDto.getMemberId());
        User user;

        if (orinUser != null) {       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        } else {      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
        }

        user.setUserArea(userLocateDto.getLocate());
        userRepository.save(user);

        return ResponseEntity.ok("success");
    }


    @ApiOperation(value = "/userInfo", notes = "유저의 추가정보를 입력합니다. (수정된게 하나여도 마이페이지 기준으로 다 넘겨줘야함")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "멤버아이디", required = true),
            @ApiImplicitParam(name = "userPhone", value = "유저 휴대폰 번호", required = true),
            @ApiImplicitParam(name = "userName", value = "유저 이름", required = true),
            @ApiImplicitParam(name = "accountBank", value = "환불 계좌은행", required = true),
            @ApiImplicitParam(name = "accountNumber", value = "환불 계좌 번호", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "memberId 없음"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/userInfo")
    public ResponseEntity<String> setUserInfo(@RequestBody UserInfoDto userInfoDto) {

        Optional<Member> member = memberRepository.findById(userInfoDto.getMemberId());

        if (!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 유저를 생성하면 안됨.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userId를 찾기 못했습니다");
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(userInfoDto.getMemberId());
        User user;

        if (orinUser != null) {       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        } else {      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
        }


        //모든 정보대로 설정
        user.setUserPhone(userInfoDto.getUserPhone());
        user.setUserRefundBank(userInfoDto.getAccountBank());
        user.setUserRefundAccount(userInfoDto.getAccountNumber());
        user.setUserName(userInfoDto.getUserName());
        user.setUserLocationAgree(true);
        user.setUserInfoAgree(true);
        user.setUserPolicyAgree(true);

        userRepository.save(user);

        return ResponseEntity.ok("success");
    }

    @ApiOperation(value = "/userInfo", notes = "유저의 추가정보를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "맴버 아이디 (서버에서 보내준 DB상 memberID)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/userInfo")
    public SingleResult<UserInfoDto> getUserInfo(@RequestParam long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);

        if (!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러

            SingleResult fail = new SingleResult();
            fail.setCode(409);
            fail.setMsg("유저 ID 가 없습니다.");
            return fail;
        }

        //이부분  findByMemberId 로 수정해줘야함. <- 로지 검증 및 테스팅 필요.
        User orinUser = userRepository.findByMemberId(memberId);
        User user;

        if (orinUser != null) {       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        } else {      //없음

            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
            userRepository.save(user);      //user 저장
        }

        UserInfoDto userInfoDto = new UserInfoDto();
        //userInfoDto.set(user.getId());
        userInfoDto.setUserPhone(user.getUserPhone());
        userInfoDto.setAccountBank(user.getUserRefundBank());
        userInfoDto.setUserName(user.getUserName());
        userInfoDto.setAccountNumber(user.getUserRefundAccount());

        return responseService.getSingleResult(userInfoDto);
    }


    @ApiOperation(value = "/keyword", notes = "유저의 키워드를 저장합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "유저(맴버) 아이디 (서버에서 보내준 DB상 memberID)", required = true),
            @ApiImplicitParam(name = "keyword", value = "유저(맴버) 아이디 (서버에서 보내준 DB상 memberID)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @PostMapping("/keyword")
    public ResponseEntity<String> insertUserKeyword(@RequestBody UserkeywordDto userkeywordDto) {

        Optional<Member> memberById = memberRepository.findById(userkeywordDto.getMemberId());

        if (!memberById.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러

            SingleResult fail = new SingleResult();
            return ResponseEntity.status(409).body("유저 ID 가 없습니다.");
        }

        User orinUser = userRepository.findByMemberId(userkeywordDto.getMemberId());
        User user;
        UserKeyword userKeyword = new UserKeyword();    //키워드마다 저장해줄 것

        if (orinUser != null) {       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        } else {      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(memberById.get().getId()); //memberId 매치
            userRepository.save(user);      //user 저장
            user = userRepository.findByMemberId(userkeywordDto.getMemberId()); //다시 userId가 포함된 객체로 리턴
        }

        userKeyword.setUser(user);  //연관관계 매핑.
        userKeyword.setKeyword(userkeywordDto.getKeyword());


        AllKeyword allKeywordResult = allKeywordRepository.findByKeyword(userkeywordDto.getKeyword());

        if (allKeywordResult == null) { //없을 경우 생성
            allKeywordResult = new AllKeyword();
            allKeywordResult.setKeyword(userkeywordDto.getKeyword());
            allKeywordResult.setCount(0);
        }

        allKeywordResult.setCount(allKeywordResult.getCount() + 1);   //1회 늘려준다.
        allKeywordRepository.save(allKeywordResult);

        if(!userKeywordRepository.existsByUserIdAndKeyword(user.getId(), userKeyword.getKeyword()))     //중복이 아닌경우만 저장.
            userKeywordRepository.save(userKeyword);//저장.

        return ResponseEntity.ok("저장 성공");
    }


    @ApiOperation(value = "/keyword", notes = "유저의 키워드를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "맴버 아이디 (서버에서 보내준 DB상 memberID)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/keyword")
    public SingleResult<UserkeywordResponseDto> getUserKeyword(@RequestParam long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);

        if (!member.isPresent()) {       //멤버 아이디 자체가 없는경우 ? 에러

            SingleResult fail = new SingleResult();
            fail.setCode(409);
            fail.setMsg("유저 ID 가 없습니다.");
            return fail;
        }

        User orinUser = userRepository.findByMemberId(memberId);
        User user;
        UserKeyword userKeyword = new UserKeyword();    //키워드마다 저장해줄 것

        if (orinUser != null) {       //유저가 이미 있다면, 기존 유저에서 업데이트
            user = orinUser;
        } else {      //없다면 새로운 유저 생성
            user = new User();
            user.setMemberId(member.get().getId()); //memberId 매치
            userRepository.save(user);      //user 저장
            user = userRepository.findByMemberId(memberId); //다시 userId가 포함된 객체로 리턴
        }

        //Return 해줄 유저 키워드 배열 객체
        UserkeywordResponseDto userkeywordResponseDto = UserkeywordResponseDto.builder()
                .userKeywords(new ArrayList<>()).build();

        List<UserKeyword> userKeywordList = userKeywordRepository.findTop10ByUserIdOrderByModifiedDateDesc(user.getId());

        int idx =1;

        for (UserKeyword keyword : userKeywordList) {
            userkeywordResponseDto.getUserKeywords().add(new KeyValueDto<Integer,String>(idx++,keyword.getKeyword()));
        }

        return responseService.getSingleResult(userkeywordResponseDto);
    }


    @ApiOperation(value = "/keyword/all", notes = "유저의 키워드를 모두 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "맴버 아이디 (서버에서 보내준 DB상 memberID)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "삭제 성공"),
            @ApiResponse(code = 409, message = "유저 ID 가 없습니다."),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @DeleteMapping("/keyword/all")
    public ResponseEntity<String> deleteAllUserKeyword(@RequestParam long memberId) {

        User user = userRepository.findByMemberId(memberId);

        if(user==null){
            return ResponseEntity.status(409).body("유저ID가 없습니다");
        }

        List<UserKeyword> userKeywordList = userKeywordRepository.findAllByUserId(user.getId());

        for (UserKeyword userKeyword : userKeywordList) {
            userKeywordRepository.deleteById(userKeyword.getId());
        }
        return ResponseEntity.ok("삭제 성공");
    }

    @ApiOperation(value = "/common/keyword/top10", notes = "가장 많이 검색된 키워드 Top10을 보여줍니다.")
    @ApiImplicitParams({
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/common/keyword/top10")
    public SingleResult<KeywordTopTenDto> getKeywordTop10() {
        //가장 높은 숫자 10개를 뽑아온다.
        List<AllKeyword> allKeywords = allKeywordRepository.findTop10ByOrderByCountDesc();

        //keyword를 감싸줄 Dto
        KeywordTopTenDto keywordTopTenDto = KeywordTopTenDto.builder().keywordTopTen(new ArrayList<>()).build();


        for (int i = 0; i < 10; i++) {
            keywordTopTenDto.getKeywordTopTen().add(new KeyValueDto<Integer,String>(i+1,allKeywords.get(i).getKeyword()));
        }

        return responseService.getSingleResult(keywordTopTenDto);
    }


    @ApiOperation(value = "/common/keyword/recommendation", notes = "유저의 검색어 자동완성(포함단어 추천)을 보여드립니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "해당단어를 기준으로 추천 키워드(포함) 조회", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
    })
    @GetMapping("/common/keyword/recommendation")
    public SingleResult<RecommendationDto> getUserKeyword(@RequestParam String keyword) {

        List<AllKeyword> allKeywordList = allKeywordRepository.findTop10ByKeywordLikeOrderByCountDesc("%"+keyword+"%");


        RecommendationDto recommendationDto = RecommendationDto.builder().recommendationTopTen(new ArrayList<>()).build();
        IntStream.range(0,(10>allKeywordList.size())?allKeywordList.size():10).forEach(i->recommendationDto.getRecommendationTopTen().add(new KeyValueDto<>(i+1,allKeywordList.get(i).getKeyword())));

        return responseService.getSingleResult(recommendationDto);
    }


}
