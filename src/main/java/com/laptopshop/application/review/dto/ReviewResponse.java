package com.laptopshop.application.review.dto;

import com.laptopshop.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {
    private Long id;
    private String userFullName;
    private Integer rating;
    private String comment;
    private Boolean isVerified;
    private LocalDateTime createdAt;

    public static ReviewResponse from(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.id = review.getId();
        dto.userFullName = review.getUser().getFullName();
        dto.rating = review.getRating();
        dto.comment = review.getComment();
        dto.isVerified = review.getIsVerified();
        dto.createdAt = review.getCreatedAt();
        return dto;
    }
}
