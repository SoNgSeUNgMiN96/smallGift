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
