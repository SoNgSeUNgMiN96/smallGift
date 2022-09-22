package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class Manager extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "manager_id")
  private Long id;


  @OneToMany(mappedBy = "manager")      //매니저의 가게 생성 매핑 연관관계
  private List<Shop> shops = new ArrayList<Shop>();


  //매니저는 정산내역을 일대다로 가질 수 있다.
  @OneToMany(mappedBy = "manager")
  private List<SettlementDetails> settlementDetails = new ArrayList<SettlementDetails>();


  @Column(nullable = false)
  private String username;

  @Column(name = "business_name", nullable = false)
  private String businessName;

  @Column(nullable = false)
  private String address;

  @Column(name = "business_tel", nullable = false)
  private String businessTel;

  @Column(name = "business_type", nullable = false)
  private String businessType;

  @Column(name = "account_holder", nullable = false)
  private String accountHolder;

  @Column(name = "settlementBank", nullable = false)
  private String settlementBank;

  @Column(name = "settlement_account", nullable = false)
  private String settlementAccount;

//    @OneToMany(mappedBy = "manager")
//    private List<Product> productList = new ArrayList<Product>();
//
//    @OneToMany(mappedBy = "manager")
//    private List<Review> reviewList = new ArrayList<Review>();

  @Column(name = "business_registration", nullable = false)
  private String businessRegistration;

  @Column(name = "mail_order_sales_registration", nullable = false)
  private String mailOrderSalesRegistraion;

  @Builder
  public Manager(Long id, String username, String businessName, String address,
      String businessTel, String businessType,
      String accountHolder, String settlementBank, String settlementAccount,
      String businessRegistration, String mailOrderSalesRegistraion) {
    this.id = id;
    this.username = username;
    this.businessName = businessName;
    this.address = address;
    this.accountHolder = accountHolder;
    this.businessTel = businessTel;
    this.businessType = businessType;
    this.settlementBank = settlementBank;
    this.settlementAccount = settlementAccount;
    this.businessRegistration = businessRegistration;
    this.mailOrderSalesRegistraion = mailOrderSalesRegistraion;
  }
}
