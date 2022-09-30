package com.sgwannabig.smallgift.springboot.dto.manager.request;

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RegistManagerDto {
  @ApiModelProperty(example = "홍길동")
  @ApiParam(value = "대표명", required = true)
  @NotNull
  private String username;

  @ApiModelProperty(example="엽떡")
  @ApiParam(value = "상호명", required = true)
  @NotNull
  private String businessName;
  @ApiModelProperty(example="서울시 마포구 양화로3길 66")
  @ApiParam(value = "주소", required = true)
  @NotNull
  private String address;
  @ApiModelProperty(example="999-99-999000")
  @ApiParam(value = "사업자 등록번호", required = true)
  @NotNull
  private String businessTel;
  @ApiModelProperty(example="종목 업태")
  @ApiParam(value = "종목 업태", required = true)
  @NotNull
  private String businessType;
  @ApiModelProperty(example="홍길동")
  @ApiParam(value = "예금주명", required = true)
  @NotNull
  private String accountHolder;
  @ApiModelProperty(example="우리은행")
  @ApiParam(value = "은행", required = true)
  @NotNull
  private String settlementBank;
  @ApiModelProperty(example="1002-000-000000")
  @ApiParam(value = "계좌", required = true)
  @NotNull
  private String settlementAccount;

  public Manager toEntity() {
    return Manager.builder()
        .username(username)
        .businessName(businessName)
        .address(address)
        .accountHolder(accountHolder)
        .businessTel(businessTel)
        .businessType(businessType)
        .settlementBank(settlementBank)
        .settlementAccount(settlementAccount)
        .build();
  }
}
