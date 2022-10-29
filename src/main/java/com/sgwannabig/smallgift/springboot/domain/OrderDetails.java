package com.sgwannabig.smallgift.springboot.domain;


import com.sgwannabig.smallgift.springboot.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    //주문 아이디.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "orderdetails_id")
    private long id;


    //어떤 상품을 샀는지 (동일 상품을 다음에 또 주문할 수 있음.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //누가 샀는지.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //주문내역을 근거할 결제번호.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;


    //리뷰는 상품별로 남길 수 있다. 리뷰가 주문내역을 참조하는것으로...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;


    @OneToMany(mappedBy = "orderDetails")
    private List<Ecoupon> ecoupons = new ArrayList<Ecoupon>();


    //주문한 주문수량
    int quantity;

    //해당 상품의 가격
    int productPrice;

    //총 주문 가격
    int totalAmount;
}
