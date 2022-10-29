package com.sgwannabig.smallgift.springboot.controller.product;

import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.product.DeleteProductUsecase;
import com.sgwannabig.smallgift.springboot.service.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shops")
@RequiredArgsConstructor
public class DeleteProductController {

  private final DeleteProductUsecase deleteProductUsecase;
  private final ResponseService responseService;

  @Operation(summary = "상품 삭제 api", description = "해당 상품을 삭제합니다")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "500", description = "서버에러"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 상품입니다"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
  })
  @DeleteMapping("/{shopId}/products/{productId}")
  public ResponseEntity<Result> deleteProduct(
      @Parameter(description = "상품 ID")
      @PathVariable Long productId) {
    deleteProductUsecase.deleteProduct(productId);
    return ResponseEntity.ok(responseService.getSuccessResult());
  }
}
