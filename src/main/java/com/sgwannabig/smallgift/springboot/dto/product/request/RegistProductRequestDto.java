package com.sgwannabig.smallgift.springboot.dto.product.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sgwannabig.smallgift.springboot.domain.Product;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class RegistProductRequestDto {

  @ApiModelProperty(example = "한식")
  @ApiParam(value = "카테고리", required = true)
  private String category;

  @ApiModelProperty(example = "김치찌개")
  @ApiParam(value = "음식이름", required = true)
  private String productName;

  @ApiModelProperty(example = "10000")
  @ApiParam(value = "제품가격", required = true)
  private int productPrice;

  @ApiModelProperty(example = "김치찌개 입니다")
  @ApiParam(value = "제품 설명", required = true)
  private String productContent;

  @ApiModelProperty(example = "10")
  @ApiParam(value = "재고", required = true)
  private long productStock;

  @ApiModelProperty(example = "2022-10-20")
  @ApiParam(value = "판매시작", required = true)
  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private String startDate;

  @ApiModelProperty(example = "2022-10-22")
  @ApiParam(value = "판매종료", required = true)
  //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private String endDate;

  public Product toEntity() {
    return Product.builder()
        .category(category)
        .productName(productName)
        .productPrice(productPrice)
        .productStock(productStock)
        .productContent(productContent)
        .status(1)
        .startDate(startDate)
        .endDate(endDate)
        .build();
  }
}
