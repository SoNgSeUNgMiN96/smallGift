package com.sgwannabig.smallgift.springboot.dto.product.response;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.domain.product.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAllProductResponseDto {

  @Schema(description = "상품 ID", example = "1")
  private Long id;
  @Schema(description = "상품명", example = "김치찌개")
  private String productName;
  @Schema(description = "상품 이미지", example = "https://url.png")
  private String productImage;
  @Schema(description = "상품 가격", example = "10000")
  private int productPrice;
  @Schema(description = "상품 상태", example = "SOLD_OUT")
  private ProductStatus productStatus;

  @Schema(description = "판매 시작 날짜", example = "2022-10-10")
  private String startDate;
  @Schema(description = "판매 종료 날짜", example = "2022-10-20")
  private String endDate;

  public GetAllProductResponseDto(Product product) {
    this.id = product.getId();
    this.productName = product.getProductName();
    this.productImage = product.getProductImage();
    this.productPrice = product.getProductPrice();
    this.productStatus = product.getProductStatus();
    this.startDate = product.getStartDate();
    this.endDate = product.getEndDate();
  }

  public static GetAllProductResponseDto from(Product product) {
    return new GetAllProductResponseDto(product);
  }
}
