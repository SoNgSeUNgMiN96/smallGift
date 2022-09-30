package com.sgwannabig.smallgift.springboot.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
public class ShopInfoDetailDto {

    private long id;

    @ApiModelProperty("양식")
    String category;

    @ApiModelProperty("카멜로 연남")
    String shopName;

    //가게 주소를 저장한다
    @ApiModelProperty("서울특별시 마포구 양화동 73번길 11")
    String shopAddress;

    //가게 전화번호를 저장한다.
    @ApiModelProperty("02-0000-0000")
    String shopTelephone;

    LocalDateTime createShopDate;

    @ApiModelProperty("버터 봉골레 파스타")
    String mainMenu;

    @ApiModelProperty("09시 - 18시")
    String businessHours;
}
