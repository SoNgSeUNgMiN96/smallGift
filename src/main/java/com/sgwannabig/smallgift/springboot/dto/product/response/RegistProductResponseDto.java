package com.sgwannabig.smallgift.springboot.dto.product.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegistProductResponseDto {

  private Long id;

  public RegistProductResponseDto(Long id) {
    this.id = id;
  }
}
