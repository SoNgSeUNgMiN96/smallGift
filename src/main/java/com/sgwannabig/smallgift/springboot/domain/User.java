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
    @Column(name = "User_Id")
    private long id;

    String userPhone;
    boolean userPolicyAgree;
    boolean userInfoAgree;
    boolean userLocationAgree;
    String userRefundBank;
    String userRefundAccount;
    String userArea;


    @OneToMany(mappedBy = "user")      //시청목록 연결관계
    private List<UserKeyword> userKeywords = new ArrayList<UserKeyword>();

}
