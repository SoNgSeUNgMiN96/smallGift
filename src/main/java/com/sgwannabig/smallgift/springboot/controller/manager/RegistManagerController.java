package com.sgwannabig.smallgift.springboot.controller.manager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sgwannabig.smallgift.springboot.config.jwt.JwtProperties;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import com.sgwannabig.smallgift.springboot.dto.manager.request.RegistManagerDto;
import com.sgwannabig.smallgift.springboot.dto.manager.response.RegistManagerResponseDto;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.manager.RegistManagerCommand;
import com.sgwannabig.smallgift.springboot.service.manager.RegistManagerUsecase;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import com.sgwannabig.smallgift.springboot.service.shop.RegistShopCommand;
import com.sgwannabig.smallgift.springboot.service.shop.RegistShopUsecase;

import com.sgwannabig.smallgift.springboot.util.aws.S3Manager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class RegistManagerController {

  private final RegistManagerUsecase registManagerUsecase;
  private final RegistShopUsecase registShopUsecase;
  private final S3Manager s3Manager;
  private final ResponseService responseService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "매니저 등록", notes = "매니저 등록을 위한 정보를 보내준")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 500, message = "서버에러"),
      @ApiResponse(code = 409, message = "이미 존재하는 사업자입니다"),
      @ApiResponse(code = 400, message = "잘못된 요청입니다")
  })
  public ResponseEntity<SingleResult> registManager(
      @RequestHeader(value = JwtProperties.HEADER_STRING, required = false)
      String authorization,
      @RequestPart("registManager")
      RegistManagerDto registManagerDto,
      @RequestPart(name = "businessRegistration")
      MultipartFile businessRegistration,
      @RequestPart(name = "mailOrderSalesRegistration")
      MultipartFile mailOrderSalesRegistration) {

    String jwt = authorization.replace(JwtProperties.TOKEN_PREFIX, "");

    //Security Context 사용 방법으로 변경 고려
    Long userId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwt)
        .getClaim("id").asLong();
    Manager registManager = registManagerDto.toEntity();
    registManager.setUserId(userId);
    Manager manager = registManagerUsecase.apply(
        new RegistManagerCommand(registManager, businessRegistration,
            mailOrderSalesRegistration));
    Shop shop = registShopUsecase.apply(new RegistShopCommand(manager));

    return ResponseEntity.ok(responseService.getSingleResult(
        new RegistManagerResponseDto(manager.getId(), shop.getId())));
  }
}
