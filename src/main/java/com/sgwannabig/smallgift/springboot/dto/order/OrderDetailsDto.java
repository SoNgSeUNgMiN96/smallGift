package com.sgwannabig.smallgift.springboot.dto.order;

import com.sgwannabig.smallgift.springboot.domain.Payment;
import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.domain.Review;
import com.sgwannabig.smallgift.springboot.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {

    //주문 아이디.

    @ApiModelProperty(example = "3")
    private long id;
    //어떤 상품을 샀는지 (동일 상품을 다음에 또 주문할 수 있음.)

    @ApiModelProperty(example = "6")
    private long productId;

    //누가 샀는지.
    @ApiModelProperty(example = "7")
    private long userId;


    //주문내역을 근거할 결제번호.
    @ApiModelProperty(example = "10385934")
    private long paymentId;


    //주문한 주문수량
    @ApiModelProperty(example = "1")
    int quantity;

    //해당 상품의 가격
    @ApiModelProperty(example = "7800")
    int productPrice;

    //총 주문 가격
    @ApiModelProperty(example = "7800")
    int totalAmount;

}
