package com.sgwannabig.smallgift.springboot.dto.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfoDetailDto {

    private long id;

    @ApiModelProperty(example = "양식")
    String category;

    @ApiModelProperty(example = "카멜로 연남")
    String shopName;

    //가게 주소를 저장한다
    @ApiModelProperty(example = "서울특별시 마포구 양화동 73번길 11")
    String shopAddress;

    //가게 전화번호를 저장한다.
    @ApiModelProperty(example = "02-0000-0000")
    String shopTelephone;

    LocalDateTime createShopDate;

    @ApiModelProperty(example = "버터 봉골레 파스타")
    String mainMenu;

    @ApiModelProperty(example = "09시 - 18시")
    String businessHours;
}
