package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class Manager {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MANAGER_ID")
    private long id;

    @NotNull
    @Column(name = "MANAGER_TEL")
    private String tel;

    @NotNull
    @Column(name = "managerBusinessName")
    private String BusinessName;
    @Column(name = "managerSettlementBank")
    private String SettlementBank;
    @Column(name = "managerSettlementAccount")
    private String SettlementAccount;


}
