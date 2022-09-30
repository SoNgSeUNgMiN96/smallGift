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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailsDto {

    //주문 아이디.
    @ApiModelProperty(example = "4")
    private long id;

    @ApiModelProperty(example = "5")
    long productId;

    //주문내역을 근거할 결제번호.
    @ApiModelProperty(example = "100423942")
    long paymentId;

    //주문한 주문수량
    @ApiModelProperty(example = "2")
    int quantity;

    //해당 상품의 가격
    @ApiModelProperty(example = "6300")
    int productPrice;

    //총 주문 가격
    @ApiModelProperty(example = "12600")
    int totalAmount;
}
