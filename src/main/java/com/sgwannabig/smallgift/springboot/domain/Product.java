package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;

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
    private long productStock;
    @NotNull
    private int status;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    private String menuImagePath;
}
