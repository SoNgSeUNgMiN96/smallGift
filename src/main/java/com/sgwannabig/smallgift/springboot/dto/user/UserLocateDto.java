package com.sgwannabig.smallgift.springboot.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocateDto {

    @ApiModelProperty(example = "21")
    long memberId;

    @ApiModelProperty(example = "경기도 광주시 오포읍")
    String locate;
}
