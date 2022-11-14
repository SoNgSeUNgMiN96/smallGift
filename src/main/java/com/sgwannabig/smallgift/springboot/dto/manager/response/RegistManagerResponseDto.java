package com.sgwannabig.smallgift.springboot.dto.manager.response;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "매니저 등록 api의 응답")
public class RegistManagerResponseDto {

  @Schema(name = "대표명", example = "홍길동")
  private String username;

  @Schema(name = "상호명", example = "엽떡")
  private String businessName;

  @Schema(name = "주소", example = "서울시 마포구 양화로3길 66")
  private String address;

  @Schema(name = "사업자 등록번호", example = "999-99-999000")
  private String businessTel;

  @Schema(name = "종목 업태", example = "요식업")
  private String businessType;

  @Schema(name = "예금주명", example = "홍길동")
  private String accountHolder;

  @Schema(name = "은행", example = "우리은행")
  private String settlementBank;

  @Schema(name = "계좌", example = "1002-000-000000")
  private String settlementAccount;

  private RegistManagerResponseDto(String username, String businessName, String address,
      String businessTel, String businessType, String accountHolder, String settlementBank,
      String settlementAccount) {
    this.username = username;
    this.businessName = businessName;
    this.address = address;
    this.businessTel = businessTel;
    this.businessType = businessType;
    this.accountHolder = accountHolder;
    this.settlementBank = settlementBank;
    this.settlementAccount = settlementAccount;
  }

  public static RegistManagerResponseDto createDto(Manager manager) {
    return new RegistManagerResponseDto(manager.getUsername(), manager.getBusinessName(),
        manager.getAddress(), manager.getBusinessTel(), manager.getBusinessType(),
        manager.getAccountHolder(), manager.getSettlementBank(), manager.getSettlementAccount());
  }
}
