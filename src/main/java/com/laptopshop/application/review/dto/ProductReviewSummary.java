package com.laptopshop.application.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class ProductReviewSummary {
    private Double averageRating;
    private Long totalReviews;
    private Page<ReviewResponse> reviews;
}
