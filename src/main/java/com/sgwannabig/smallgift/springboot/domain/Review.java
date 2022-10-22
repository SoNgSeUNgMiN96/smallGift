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
public class Review extends BaseTimeEntity {        //BaseTime에 생성 및 수정일이 담겨있음.

    //리뷰 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;


    //한 상품에 대한 리뷰를 남긴다. (동일 상품을 다음에 또 남길 수 있음.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //어떤 가게의 리뷰인지를 담고있다. (동일 가게에서 리뷰를 여러번 남길 수 있음.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;


    //누가 리뷰를 남기었는지를 담고 있다. 여러 유저가 남길 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //Todo
    //상품은 여러개의 주문 내역을 가질 수 있다.
    @OneToMany(mappedBy = "review")
    private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();


    //리뷰 제목
    @NotNull
    private String reviewTitle;

    //리뷰 내용.
    @NotNull
    private String reviewContents;

    //리뷰 이미지 (S3와 연동될 위치?)
    @NotNull
    private String reviewImage;
}
