package com.sgwannabig.smallgift.springboot.dto.shop;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class ProductInfoDto {

    Long id;

    String category;

    String productName;

    int productPrice;

    int discountPrice;

    long productStock;

    int status;

    String createDate;

    String startDate;

    String endDate;
}
