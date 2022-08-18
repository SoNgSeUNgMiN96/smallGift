package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProductRequestDto {
    @ApiModelProperty(value = "15")
    long productId;

    @ApiModelProperty(value = "육회비빔밥")
    String productName;

    @ApiModelProperty(value = "8900")
    int productPrice;

    @ApiModelProperty(value = "17")
    long productStock;

    @ApiModelProperty(value = "20220907")
    String start_dt;

    @ApiModelProperty(value = "20230904")
    String end_dt;

    @ApiModelProperty(value = "/test/storeFood.jpg")
    private MultipartFile imageFile;
}
