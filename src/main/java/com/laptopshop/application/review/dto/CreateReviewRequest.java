package com.laptopshop.application.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewRequest {
    private Long productId;
    private Long orderItemId;
    private Integer rating;   // 1 - 5
    private String comment;
}
