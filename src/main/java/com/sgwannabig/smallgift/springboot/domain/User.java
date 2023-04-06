package com.sgwannabig.smallgift.springboot.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_Id")
    private long id;

    //유저는 여러 리뷰를 남길 수 있다..
    @OneToMany(mappedBy = "user")
    private List<Review> review = new ArrayList<Review>();

    @OneToMany(mappedBy = "user")      //유저의 검색어 목부
    private List<UserKeyword> userKeywords = new ArrayList<UserKeyword>();


    //유저는 여러개의 주문 내역을 가질 수 있다.
    @OneToMany(mappedBy = "user")
    private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

    //유저는 여러개의 환불 내역을 가질 수 있다.
    @OneToMany(mappedBy = "user")
    private List<RefundDetails> refundDetails = new ArrayList<RefundDetails>();

    private long memberId;

    String userName;
    String userPhone;
    boolean userPolicyAgree;
    boolean userInfoAgree;
    boolean userLocationAgree;
    String userRefundBank;
    String userRefundAccount;
    String userArea;
}
