package com.sgwannabig.smallgift.springboot.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLocateDto {

    @ApiModelProperty(example = "21")
    long userId;

    @ApiModelProperty(example = "경기도 광주시 오포읍")
    String locate;
}
