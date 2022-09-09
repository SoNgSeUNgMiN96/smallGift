package com.sgwannabig.smallgift.springboot.dto.shop;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
public class ShopInfoDetailDto {

    private long id;

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
    LocalDateTime createShopDate;

    String mainMenu;

    String businessHours;


}
