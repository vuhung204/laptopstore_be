package com.laptopshop.domain.review.repository;

import com.laptopshop.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByProductIdAndIsVisibleTrue(Long productId, Pageable pageable);

    boolean existsByUserIdAndOrderItemId(Long userId, Long orderItemId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.isVisible = true")
    Optional<Double> findAverageRatingByProductId(@Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.isVisible = true")
    Long countByProductId(@Param("productId") Long productId);
}
