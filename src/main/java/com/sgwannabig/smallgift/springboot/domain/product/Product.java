package com.sgwannabig.smallgift.springboot.domain.product;

import com.sgwannabig.smallgift.springboot.domain.OrderDetails;
import com.sgwannabig.smallgift.springboot.domain.Review;
import com.sgwannabig.smallgift.springboot.domain.shop.Shop;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Product{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
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

  private String productImage;

  private String productContent;
  @NotNull
  private int status;

  private String productBuyer;

  @NotNull
  private String createDate;
  @NotNull
  private String startDate;

  @NotNull
  private String endDate;

  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;

  //메뉴가 받은 총 좋아요 수
  @Column(nullable = false)
  long likeCount;

  @Builder
  public Product(Long id, Shop shop, List<Review> review, List<OrderDetails> orderDetails,
      String category, String productName, int productPrice, int discountPrice, long productStock,
      String productImage, String productContent, int status, String productBuyer,
      String createDate,
      String startDate, String endDate, long likeCount, ProductStatus productStatus) {
    this.id = id;
    this.shop = shop;
    this.review = review;
    this.orderDetails = orderDetails;
    this.category = category;
    this.productName = productName;
    this.productPrice = productPrice;
    this.discountPrice = discountPrice;
    this.productStock = productStock;
    this.productImage = productImage;
    this.productContent = productContent;
    this.status = status;
    this.productBuyer = productBuyer;
    this.createDate = createDate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.likeCount = likeCount;
    this.productStatus = productStatus;
  }
}
