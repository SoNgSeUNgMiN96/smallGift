package com.sgwannabig.smallgift.springboot.domain;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shop extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "shop_Id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    //가게는 메뉴를 0 - 1 - many 가질 수 있다.
    @OneToMany(mappedBy = "shop")
    private List<Product> products = new ArrayList<Product>();


    //가게는 여러 리뷰를 가진다.
    @OneToMany(mappedBy = "shop")
    private List<Review> review = new ArrayList<Review>();



    //카테고리를 저장한다.
    String category;

    //가게 이름을 저장한다
    String shopName;

    //가게 주소를 저장한다
    String shopAddress;

    //가게 전화번호를 저장한다.
    String shopTelephone;

    //가게의 승인 여부를 저장한다.
    boolean isAllowed;

    //가게 생성 일자를 저장한다.
    @CreatedDate
    private LocalDateTime createShopDate;

    //가게 대표메뉴 (한줄 미리보기 영역에 저장될 검색어)
    String mainMenu;

    //가게 영업시간 정보. 텍스트로 직접 설정하고 편집할 말을 적는다.
    String businessHours;

}
