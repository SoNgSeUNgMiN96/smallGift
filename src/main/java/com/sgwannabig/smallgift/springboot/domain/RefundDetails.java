package com.sgwannabig.smallgift.springboot.domain;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RefundDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "refund_details_id")
    private long id;


    //유저는 많은 환불내역을 갖는다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //E-coupon은 하나의 환불 내역을 갖거나 갖지 못한다. (하지만 참조관계 때문에 이렇게 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecoupon_id")
    private Ecoupon ecoupon;


    //환불 상태. true가 정산완료
    private boolean refundStatus;

    //환불 날짜.
    @CreatedDate
    private LocalDateTime refundtTime;

}
