package com.sgwannabig.smallgift.springboot.controller.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.dto.product.response.GetAllProductResponseDto;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.product.GetAllProductUsecase;
import com.sgwannabig.smallgift.springboot.service.result.MultipleResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shops")
@RequiredArgsConstructor
public class GetAllProductController {

  private final GetAllProductUsecase getAllProductUsecase;
  private final ResponseService responseService;


  @Operation(summary = "전체 상품목록 조회 api", description = "해당 가게의 전체 상품 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "500", description = "서버에러"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 가게입니다"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다")
  })
  @GetMapping("/{shopId}/products")
  public ResponseEntity<MultipleResult<GetAllProductResponseDto>> getAllProduct(
      @Parameter(description = "가게 ID",required = true)
      @PathVariable Long shopId) {

    List<Product> allProduct = getAllProductUsecase.getAllProduct(shopId);
    System.out.println(allProduct.size());

    return ResponseEntity.ok(responseService.getMultipleResult(
        allProduct.stream().map(product -> GetAllProductResponseDto.from(product))
            .collect(Collectors.toList())));
  }
}
