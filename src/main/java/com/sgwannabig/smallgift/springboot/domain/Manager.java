package com.sgwannabig.smallgift.springboot.domain;


import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MANAGER_ID")
    // @OneToMany(mappedBy = "{shop}", "{settlement_datails}", fecth = FetchType.LAZY)
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
    @Column(name = "managerSettlementBank")
    private String settlementBank;

    @NotNull
    @Column(name = "managerSettlementAccount")
    private String settlementAccount;

//    private UploadFile managerAttachFile;
//    private UploadFile salesAttachFile;
}
