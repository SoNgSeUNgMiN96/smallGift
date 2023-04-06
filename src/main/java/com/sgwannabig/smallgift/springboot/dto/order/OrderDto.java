package com.sgwannabig.smallgift.springboot.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;




@Data
public class OrderDto {

    @ApiModelProperty(example = "4",dataType = "long", notes = "유저의 member아이디입니다.")
    long memberId;

    @ApiModelProperty(example = "4",dataType = "long", notes = "상품의 아이디입니다.")
    long productId;
}
