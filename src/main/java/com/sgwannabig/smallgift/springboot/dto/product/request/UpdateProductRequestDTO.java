package com.sgwannabig.smallgift.springboot.dto.product.request;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.domain.product.ProductStatus;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(name = "updateProductRequestDto", description = "제품 수정 요청 dto")
public class UpdateProductRequestDTO {
    @Schema(name = "카테고리", example = "한식")
    @NotNull
    private String category;

    @Schema(name = "음식이름", example = "김치찌개")
    @NotNull
    private String productName;

    @Schema(name = "제품가격", example = "10000")
    @NotNull
    private int productPrice;

    @Schema(name = "제품 설명", example = "김치찌개 입니다")
    @NotNull
    private String productContent;

    @Schema(name = "재고", example = "10")
    @NotNull
    private long productStock;

    @Schema(name = "판매시작", example = "2022-10-20")
    @NotNull
    private String startDate;

    @Schema(name = "판매종료", example = "2022-10-20")
    @NotNull
    private String endDate;

    public Product toEntity() {
        return Product.builder()
            .category(category)
            .productName(productName)
            .productPrice(productPrice)
            .productStock(productStock)
            .productContent(productContent)
            .productStatus(ProductStatus.SOLD_OUT)
            .status(1)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }
}
