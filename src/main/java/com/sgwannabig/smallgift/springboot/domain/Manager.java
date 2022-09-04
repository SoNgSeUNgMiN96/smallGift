package com.sgwannabig.smallgift.springboot.domain;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class Manager extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;


    @OneToMany(mappedBy = "manager")      //매니저의 가게 생성 매핑 연관관계
    private List<Shop> shops = new ArrayList<Shop>();


    //매니저는 정산내역을 일대다로 가질 수 있다.
    @OneToMany(mappedBy = "manager")
    private List<SettlementDetails> settlementDetails = new ArrayList<SettlementDetails>();


    @NotNull
    private String username;

    @NotNull
    private String businessName;
    @NotNull
    private String address;
    @NotNull
    private String businessTel;
    @NotNull
    private String businessType;

    @NotNull
    private String settlementBank;

    @NotNull
    private String settlementAccount;

//    @OneToMany(mappedBy = "manager")
//    private List<Product> productList = new ArrayList<Product>();
//
//    @OneToMany(mappedBy = "manager")
//    private List<Review> reviewList = new ArrayList<Review>();

//    private UploadFile managerAttachFile;
//    private UploadFile salesAttachFile;
}
