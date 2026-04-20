package com.laptopshop.domain.review.entity;

import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.order.entity.OrderItem;
import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.store.entity.Staff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_review_user_item",
                        columnNames = {"user_id", "order_item_id"}
                )
        },
        indexes = {
                @Index(name = "idx_review_product", columnList = "product_id")
        }
)
@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replied_by")
    private Staff repliedBy;

    @Column(nullable = false)
    private Integer rating; // 1-5

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible = true;

    @Column(name = "reply_text", columnDefinition = "TEXT")
    private String replyText;

    @Column(name = "replied_at")
    private LocalDateTime repliedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
