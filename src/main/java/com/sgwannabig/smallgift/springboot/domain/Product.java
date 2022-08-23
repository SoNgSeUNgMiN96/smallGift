package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @NotNull
    private String category;

    @NotNull
    private String productName;

    @NotNull
    private int productPrice;
    @NotNull
    private int discountPrice;

    @NotNull
    private long productStock;

    @NotNull
    private int status;

    private String productBuyer;

    @NotNull
    private String createDate;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
//    private Manager manager;

//    private UploadFile imageFile;
}
