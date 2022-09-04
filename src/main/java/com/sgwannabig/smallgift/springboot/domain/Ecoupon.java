package com.sgwannabig.smallgift.springboot.domain;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ecoupon extends BaseTimeEntity{


    //Ecoupon 아이디.
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


    //e쿠폰은 정산 내역을 가질 수 있다. 1개 또는 0개지만 연관관계 매핑을 위해서 이렇게 잡음.
    @OneToMany(mappedBy = "ecoupon")
    private List<SettlementDetails> settlementDetails = new ArrayList<SettlementDetails>();

    //e쿠폰은 환불 내역을 가질 수 있다. 1개 또는 0개지만 연관관계 매핑을 위해서 이렇게 잡음.
    @OneToMany(mappedBy = "ecoupon")
    private List<RefundDetails> refundDetails = new ArrayList<RefundDetails>();


    //이건 테스팅 해봐야함. 사용여부를 enum으로 받는다.   Y, N, R 값만 갖도록 함       //사용, 미사용, 환불  <- enum호환성때문에 그냥 로직으로 구분하기.
    private String useState;

    //생성된 쿠폰번호.
    private String couponNumber;

    //사용기한.
    @CreatedDate
    private LocalDateTime expirationTime;

    //사용일자.
    @CreatedDate
    private LocalDateTime usedTime;
}

