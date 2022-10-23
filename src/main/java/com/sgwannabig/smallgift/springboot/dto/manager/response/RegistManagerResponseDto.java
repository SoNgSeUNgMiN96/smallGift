package com.sgwannabig.smallgift.springboot.dto.manager.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegistManagerResponseDto {

  Long managerId;
  Long shopId;

  public RegistManagerResponseDto(Long managerId, Long shopId) {
    this.managerId = managerId;
    this.shopId = shopId;
  }

}
