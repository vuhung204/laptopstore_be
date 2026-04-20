package com.laptopshop.interfaces.rest;

import com.laptopshop.application.review.dto.CreateReviewRequest;
import com.laptopshop.application.review.dto.ProductReviewSummary;
import com.laptopshop.application.review.dto.ReviewResponse;
import com.laptopshop.application.review.service.ReviewService;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"))
                .getId();
    }

    // POST /api/reviews
    @PostMapping("/api/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            Authentication auth,
            @RequestBody CreateReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(getUserId(auth), request));
    }

    // GET /api/products/{id}/reviews
    @GetMapping("/api/products/{id}/reviews")
    public ResponseEntity<ProductReviewSummary> getProductReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getProductReviews(id, page, size));
    }
}
