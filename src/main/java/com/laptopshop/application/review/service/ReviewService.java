package com.laptopshop.application.review.service;

import com.laptopshop.application.review.dto.CreateReviewRequest;
import com.laptopshop.application.review.dto.ProductReviewSummary;
import com.laptopshop.application.review.dto.ReviewResponse;
import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.catalog.repository.ProductRepository;
import com.laptopshop.domain.order.entity.OrderItem;
import com.laptopshop.domain.order.repository.OrderItemRepository;
import com.laptopshop.domain.review.entity.Review;
import com.laptopshop.domain.review.repository.ReviewRepository;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponse createReview(Long userId, CreateReviewRequest request) {
        // Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating phải từ 1 đến 5");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Product product = productRepository.findByIdAndIsActiveTrue(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Kiểm tra đã mua hàng chưa (qua orderItemId)
        OrderItem orderItem = null;
        if (request.getOrderItemId() != null) {
            orderItem = orderItemRepository.findById(request.getOrderItemId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy order item"));

            // Kiểm tra đã review order item này chưa
            if (reviewRepository.existsByUserIdAndOrderItemId(userId, request.getOrderItemId())) {
                throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi");
            }
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setOrderItem(orderItem);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setIsVerified(orderItem != null); // verified nếu đã mua hàng
        review.setIsVisible(true);

        reviewRepository.save(review);
        return ReviewResponse.from(review);
    }

    public ProductReviewSummary getProductReviews(Long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<ReviewResponse> reviews = reviewRepository
                .findAllByProductIdAndIsVisibleTrue(productId, pageable)
                .map(ReviewResponse::from);

        Double avgRating = reviewRepository.findAverageRatingByProductId(productId)
                .orElse(0.0);

        Long totalReviews = reviewRepository.countByProductId(productId);

        return new ProductReviewSummary(avgRating, totalReviews, reviews);
    }
}
