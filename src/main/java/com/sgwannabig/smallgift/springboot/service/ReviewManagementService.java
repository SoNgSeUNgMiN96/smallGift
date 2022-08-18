package com.sgwannabig.smallgift.springboot.service;

import com.sgwannabig.smallgift.springboot.domain.Product;
import com.sgwannabig.smallgift.springboot.domain.Review;
import com.sgwannabig.smallgift.springboot.dto.review.ReviewResponseDto;
import com.sgwannabig.smallgift.springboot.dto.sales.ShopProductDto;
import com.sgwannabig.smallgift.springboot.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewManagementService {
    private final ReviewRepository reviewRepository;
    // 리뷰 조회
    public List<ReviewResponseDto> showReviews(Long shopId) {
        List<Review> reviews = reviewRepository.findAllById(shopId);
        if(reviews.isEmpty()) {
            return null;
        }

        List<ReviewResponseDto> reviewResponseDtos = new ArrayList<>();
        long listNum = 1;
        for(Review review : reviews) {
            ReviewResponseDto temp = new ReviewResponseDto();
            temp.setListNum(listNum++);
            temp.setReviewId(review.getId());
            temp.setReviewTitle(review.getReviewTitle());
            temp.setReviewContents(review.getReviewContents());
            temp.setReviewDate(review.getReviewDate());
            reviewResponseDtos.add(temp);
        }
        return reviewResponseDtos;
    }
}
