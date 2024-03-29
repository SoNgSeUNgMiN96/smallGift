package com.sgwannabig.smallgift.springboot.controller.product;

import com.sgwannabig.smallgift.springboot.domain.product.Product;
import com.sgwannabig.smallgift.springboot.dto.product.request.RegistProductRequestDto;
import com.sgwannabig.smallgift.springboot.dto.product.response.RegistProductResponseDto;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.product.RegistProductCommand;
import com.sgwannabig.smallgift.springboot.service.product.RegistProductUsecase;
import com.sgwannabig.smallgift.springboot.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/managers/{managerId}")
@RequiredArgsConstructor
public class RegistProductController {

  private final RegistProductUsecase registProductUsecase;
  private final ResponseService responseService;

  @PostMapping("/shops/products")
  @ApiOperation(value = "제품 등록", notes = "제품 등록을 하기위한 정보를 보내준다.")
  @ApiResponses({
      @ApiResponse(code = 201, message = "성공"),
      @ApiResponse(code = 500, message = "서버에러"),
      @ApiResponse(code = 404, message = "이미 존재하는 정보입니다."),
      @ApiResponse(code = 400, message = "잘못된 요청입니다")
  })
  public ResponseEntity<SingleResult> registProduct(
      @Parameter(description = "매니저 ID", required = true)
      @PathVariable Long managerId,
      @RequestPart("registProduct") RegistProductRequestDto registProductRequestDto,
      @RequestPart MultipartFile productImage) {

    Product product = registProductRequestDto.toEntity();

    Product registedproduct = registProductUsecase.apply(
        new RegistProductCommand(managerId, product, productImage));

    URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
        .buildAndExpand(product.getId()).toUri();

    return ResponseEntity.created(uri)
        .body(responseService.getSingleResult(new RegistProductResponseDto(
            registedproduct.getId())));
  }
}
