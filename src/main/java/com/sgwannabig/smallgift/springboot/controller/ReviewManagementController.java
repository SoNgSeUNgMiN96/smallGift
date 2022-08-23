package com.sgwannabig.smallgift.springboot.controller;

import com.sgwannabig.smallgift.springboot.domain.Review;
import com.sgwannabig.smallgift.springboot.dto.review.ReviewResponseDto;
import com.sgwannabig.smallgift.springboot.dto.sales.ShopProductDto;
import com.sgwannabig.smallgift.springboot.service.ReviewManagementService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("api/manager")
@RequiredArgsConstructor
public class ReviewManagementController {
    private final ReviewManagementService reviewManagementService;
    @ApiOperation(value = "리뷰 조회", notes = "해당 가게의 리뷰들을 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 405, message = "올바른 요청을 해주세요.")
    })
    @GetMapping("/review/all/{shopId}")
    public List<ReviewResponseDto> productManagement(@PathVariable Long shopId) {
        List<ReviewResponseDto> reviews = reviewManagementService.showReviews(shopId);
        if(reviews == null) {
            return null;
        }

        return reviews;
    }

}
