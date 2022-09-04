package com.sgwannabig.smallgift.springboot.domain;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SettlementDetails extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "settlement_details_id")
    private long id;


    //메니저는 많은 정산내역을 갖는다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;


    //E-coupon은 하나의 정산 내역을 갖거나 갖지 못한다. (하지만 참조관계 때문에 이렇게 매핑)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecoupon_id")
    private Ecoupon ecoupon;


    //정산 상태. true가 정산완료
    private boolean settlementStatus;

    //정산 날짜.
    @CreatedDate
    private LocalDateTime settlementTime;
}