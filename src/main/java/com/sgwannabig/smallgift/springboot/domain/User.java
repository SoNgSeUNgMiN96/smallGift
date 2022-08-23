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

    private long memberId;

    String userPhone;
    boolean userPolicyAgree;
    boolean userInfoAgree;
    boolean userLocationAgree;
    String userRefundBank;
    String userRefundAccount;
    String userArea;


    @OneToMany(mappedBy = "user")      //유저의 검색어 목부
    private List<UserKeyword> userKeywords = new ArrayList<UserKeyword>();

//    @OneToMany(mappedBy = "user")
//    private List<Review> reviews = new ArrayList<Review>();

}
