package com.sgwannabig.smallgift.springboot.dto.manager.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegistManagerResponseDto {
  Long id;

  public RegistManagerResponseDto(Long id) {
    this.id = id;
  }
}
