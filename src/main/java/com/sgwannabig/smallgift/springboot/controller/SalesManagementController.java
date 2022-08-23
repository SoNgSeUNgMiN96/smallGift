package com.sgwannabig.smallgift.springboot.controller;

import com.sgwannabig.smallgift.springboot.domain.Manager;
import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.dto.sales.*;
import com.sgwannabig.smallgift.springboot.repository.ManagerRepository;
import com.sgwannabig.smallgift.springboot.repository.ProductRepository;
import com.sgwannabig.smallgift.springboot.service.SalesManagementService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class SalesManagementController {
    private final SalesManagementService salesManagementService;
    private final ManagerRepository managerRepository;
//    private final S3Service s3Service;
    //private final FileStore fileStore;

    @ApiOperation(value = "관리자 메인", notes = "관리자 메인 페이지입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음")
    })
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    @ApiOperation(value = "사업자 등록", notes = "사업자 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="managerId", value ="사업자 ID", required = true),
            @ApiImplicitParam(name="username", value ="대표자명", required = true),
            @ApiImplicitParam(name="businessName", value ="상호명", required = true),
            @ApiImplicitParam(name="address", value ="사업자 주소", required = true),
            @ApiImplicitParam(name="businessTel", value ="사업자 등록 번호", required = true),
            @ApiImplicitParam(name="businessType", value ="업종", required = true),
            @ApiImplicitParam(name="bankName", value ="은행", required = true),
            @ApiImplicitParam(name="bankAccount", value ="계좌", required = true),
            @ApiImplicitParam(name="managerImage", value ="대표 이미지", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 사업자입니다."),
            @ApiResponse(code = 402, message = "등록 번호 형식을 유지해주세요."),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("/registration/manager")
    public ManagerResponseDto managerRegistration(@RequestBody ManagerDto managerDto) throws IOException {
        Manager manager = salesManagementService.saveManager(managerDto);

        ManagerResponseDto managerResponseDto = new ManagerResponseDto();
        if(manager != null) {
            managerResponseDto.setCode("200");
            managerResponseDto.setMessage("사업자가 등록되었습니다.");
            managerResponseDto.setManagerId(managerDto.getManagerId());
        }
        return managerResponseDto;
    }

//    @PostMapping("/registration/manager") // multipart/form-data 방식으로 요청
//    public void uploadFile(@RequestParam MultipartFile multipartFile) throws IOException {
//        s3Service.saveUploadFile(multipartFile);
//    }

//    @GetMapping("/registration/manager/{managerId}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable Long managerId)
//            throws MalformedURLException {
//        Manager manager = managerRepository.findById(managerId).orElse(null);
//        String storeFileName = manager.getManagerAttachFile().getStoreFileName();
//        String uploadFileName = manager.getSalesAttachFile().getUploadFileName();
//
//        UrlResource resource = new UrlResource("file:" +
//                fileStore.getFullPath(storeFileName));
//
//        String encodedUploadFileName = UriUtils.encode(uploadFileName,
//                StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\"" +
//                encodedUploadFileName + "\"";
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//    }

    @ApiOperation(value = "상품 등록", notes = "상품 등록 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 401, message = "이미 존재하는 상품입니다."),
            @ApiResponse(code = 402, message = "이미지 형식을 유지해주세요."),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("/registration/product")
    public ProductResponseDto productRegistration(@RequestBody ProductRequestDto productRequestDto) throws IOException {
        Product product = salesManagementService.saveProduct(productRequestDto);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        if(product != null) {
            productResponseDto.setCode("200");
            productResponseDto.setMessage("상품이 등록되었습니다.");
            productResponseDto.setProductId(productRequestDto.getProductId());
        }
        return productResponseDto;
    }

//    @GetMapping("/registration/product/{filename}")
//    public Resource downloadImage(@PathVariable String filename) throws
//            MalformedURLException {
//        return new UrlResource("file:" + fileStore.getFullPath(filename));
//    }

    @ApiOperation(value = "상품 관리", notes = "가게의 상품들을 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/productList/{shopId}")
    public List<ShopProductDto> productManagement(@PathVariable Long shopId) {
        List<ShopProductDto> products = salesManagementService.showProducts(shopId);
        if(products == null) {
            return null;
        }
        return products;
    }

    @ApiOperation(value = "상품 노출 상태 변경", notes = "상품 상태 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productId", value ="상품 코드", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PatchMapping("/productList/status/{productId}")
    public ResponseEntity<String> productStatus(@PathVariable Long productId) {
        boolean changeStatus = salesManagementService.changeProductStatus(productId);

        ResponseEntity<String> statusMessage = new ResponseEntity<>(HttpStatus.OK);

        return statusMessage;
    }

    @ApiOperation(value = "상품 삭제", notes = "상품 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name="productId", value ="상품 코드(ID)", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "상품 삭제 완료"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @DeleteMapping("/productList/remove/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId) {
        String deleteMessage = salesManagementService.deleteProduct(productId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(deleteMessage, headers, HttpStatus.OK);
    }


    @ApiOperation(value = "상품 수정", notes = "상품 수정 API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "상품 수정 완료"),
            @ApiResponse(code = 500, message = "서버 에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @PostMapping("/productList/modification")
    public ResponseEntity<UpdateProductResponseDto> productModification(@RequestBody UpdateProductRequestDto updateProductRequestDto) throws IOException {
        Product product = salesManagementService.updateProduct(updateProductRequestDto);

        if(product == null) throw new NoSuchElementException();

        UpdateProductResponseDto updateProductResponseDto = new UpdateProductResponseDto();
        updateProductResponseDto.setProductId(updateProductRequestDto.getProductId());
        updateProductResponseDto.setProductName(updateProductRequestDto.getProductName());
        updateProductResponseDto.setProductPrice(updateProductRequestDto.getProductPrice());
        updateProductResponseDto.setProductStock(updateProductRequestDto.getProductStock());
        updateProductResponseDto.setStart_dt(updateProductRequestDto.getStart_dt());
        updateProductResponseDto.setEnd_dt(updateProductRequestDto.getEnd_dt());

        return ResponseEntity.ok(updateProductResponseDto);
    }

}
