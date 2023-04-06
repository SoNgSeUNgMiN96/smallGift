package com.sgwannabig.smallgift.springboot.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class OrderedProduct {
    @ApiModelProperty(example = "4",dataType = "long", notes = "상품의 아이디입니다.")
    long productId;

    @ApiModelProperty(example = "1",dataType = "int", notes = "상품의 수량입니다.")
    int qty;
}
