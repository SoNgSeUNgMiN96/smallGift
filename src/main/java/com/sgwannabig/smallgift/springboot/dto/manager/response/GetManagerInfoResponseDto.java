package com.sgwannabig.smallgift.springboot.dto.manager.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "매니저 정보 요청 api의 응답")
public class GetManagerInfoResponseDto {
  @Schema(name = "매니저 ID", example = "1")
  private Long managerId;

  public GetManagerInfoResponseDto(Long managerId) {
    this.managerId = managerId;
  }
}
