package com.sgwannabig.smallgift.springboot.domain;


import javax.persistence.*;


/*
    찜은 유저가 특정 상품을 찜 한다.
    유저는 여러 상품을 찜 할 수 있고, 찜이 된 상품의 정보를 갖고있다.
 */
@Entity
public class WishList extends BaseTimeEntity {      //생성 및 수정일은 BaseTimeEntity를 통해 관리

    //id로 관리하기 위해 적은것.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "wishlist_Id")
    private long id;

    //한 상품이 여러번 찜 될 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //유저가 찜을 여러개 할 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
