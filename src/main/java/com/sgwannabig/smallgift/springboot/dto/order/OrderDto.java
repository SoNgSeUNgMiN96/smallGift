package com.sgwannabig.smallgift.springboot.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;




@Data
public class OrderDto {

    @ApiModelProperty(example = "4",dataType = "long", notes = "유저의 아이디입니다.")
    long userId;

    @ApiModelProperty(example = "010-0000-0000", dataType = "String", notes = "받는사람 휴대폰번호입니다")
    String receiverPhoneNumber;

    List<OrderedProduct> orderedProductList;
}
