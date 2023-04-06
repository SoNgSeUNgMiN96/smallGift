package com.sgwannabig.smallgift.springboot.dto.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequestDto {
    @ApiModelProperty(value ="3")
    private long managerId;

    @ApiModelProperty(value = "7")
    private long productId;

    @ApiModelProperty(value = "한식")
    private String category;

    @ApiModelProperty(value = "김치찌개")
    private String productName;

    @ApiModelProperty(value = "7000")
    private int productPrice;

    @ApiModelProperty(value = "15")
    private long productStock;

    @ApiModelProperty(value = "0")
    private int status;

    @ApiModelProperty(value = "20220804")
    private String start_dt;

    @ApiModelProperty(value = "20230802")
    private String end_dt;

    @ApiModelProperty(value = "/test/storeFood.jpg")
    private MultipartFile imageFile;

}
