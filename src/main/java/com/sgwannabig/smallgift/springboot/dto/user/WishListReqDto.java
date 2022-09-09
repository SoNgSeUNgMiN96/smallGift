package com.sgwannabig.smallgift.springboot.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WishListReqDto {

    @ApiModelProperty("16")
    long userId;
    @ApiModelProperty("125")
    long productId;
}
