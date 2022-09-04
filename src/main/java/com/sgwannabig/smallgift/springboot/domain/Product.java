package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;


    //가게는 많은 상품을 갖는다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    //상품은 여러 리뷰를 갖는다.
    @OneToMany(mappedBy = "product")
    private List<Review> review = new ArrayList<Review>();

    //상품은 여러개의 주문 내역을 가질 수 있다.
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();


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


}
