package com.sgwannabig.smallgift.springboot.dto.shop;


import com.sgwannabig.smallgift.springboot.domain.product.Product;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopInfoDto {


    @ApiModelProperty(example = "카페")
    String category;

    @ApiModelProperty(example = "을지다락 강남")
    String shopName;

    @ApiModelProperty(example = "서울 강남구 강남대로9실 22 2층")
    String address;

    @ApiModelProperty(example = "아이스 아메리카노")
    String mainMenu;

    @ApiModelProperty(example = "4")
    long shopId;

    String shopThumbnailImage;

    String shopInfoImage;
}
