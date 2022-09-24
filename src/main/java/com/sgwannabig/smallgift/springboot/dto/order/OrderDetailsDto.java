package com.sgwannabig.smallgift.springboot.dto.order;

import com.sgwannabig.smallgift.springboot.domain.Payment;
import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.domain.Review;
import com.sgwannabig.smallgift.springboot.domain.User;
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
    private long id;
    //어떤 상품을 샀는지 (동일 상품을 다음에 또 주문할 수 있음.)
    private long productId;

    //누가 샀는지.
    private long userId;


    //주문내역을 근거할 결제번호.
    private long paymentId;

    //주문한 주문수량
    int quantity;

    //해당 상품의 가격
    int productPrice;

    //총 주문 가격
    int totalAmount;

}
