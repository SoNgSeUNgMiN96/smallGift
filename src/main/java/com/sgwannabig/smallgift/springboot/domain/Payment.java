package com.sgwannabig.smallgift.springboot.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Payment extends BaseTimeEntity{
    //결제 아이디.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "payment_id")
    private long id;

    //해당 결제로 여러 주문 내역을 남길 수 있다.
    @OneToMany(mappedBy = "payment")
    private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

    //결제 가격
    int payPrice;

    //결제 수단.
    String payMethod;

    //결제 성공 여부
    boolean payCheck;

    //결제 일자 밀 수정일은 BaseTimeEntity에 담김.


}
