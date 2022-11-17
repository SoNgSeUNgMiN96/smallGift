package com.sgwannabig.smallgift.springboot.controller.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sgwannabig.smallgift.springboot.config.jwt.JwtProperties;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.dto.manager.response.GetManagerInfoResponseDto;
import com.sgwannabig.smallgift.springboot.dto.product.response.GetAllProductResponseDto;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.manager.GetManagerInfoUsecase;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/manager/info")
@RequiredArgsConstructor
public class GetManagerInfoController {

  private final GetManagerInfoUsecase getManagerInfoUsecase;
  private final ResponseService responseService;

  @GetMapping
  @ApiOperation(value = "매니저 ID 확인", notes = "토큰 정보로 등록된 매니저 ID를 보내준다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 500, message = "서버에러"),
      @ApiResponse(code = 409, message = "이미 존재하는 사업자입니다"),
      @ApiResponse(code = 400, message = "잘못된 요청입니다")
  })
  public ResponseEntity<SingleResult> getManagerInfo(
      @RequestHeader(value = JwtProperties.HEADER_STRING)
      String authorization){
    String jwt = authorization.replace(JwtProperties.TOKEN_PREFIX, "");

    //Security Context 사용 방법으로 변경 고려
    Long userId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwt)
        .getClaim("id").asLong();

    Manager managerInfo = getManagerInfoUsecase.getManagerInfo(userId);

    return ResponseEntity.ok(responseService.getSingleResult(new GetManagerInfoResponseDto(managerInfo.getId())));
  }
}
