package com.sgwannabig.smallgift.springboot.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserkeywordDto {
    @ApiModelProperty(example = "12")
    long memberId;
    @ApiModelProperty(example = "아이스 아메리카노")
    String keyword;
}
