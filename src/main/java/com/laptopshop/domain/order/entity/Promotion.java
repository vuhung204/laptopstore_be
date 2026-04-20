package com.laptopshop.domain.order.entity;

import com.laptopshop.domain.order.enums.DiscountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Table(
        name = "promotions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_promo_code", columnNames = "code")
        },
        indexes = {
                @Index(name = "idx_promo_dates", columnList = "starts_at, ends_at")
        }
)
@Entity
@Getter
@Setter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType = DiscountType.PERCENT;

    @Column(name = "discount_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "max_discount", precision = 15, scale = 0)
    private BigDecimal maxDiscount;

    @Column(name = "min_order_amount", nullable = false, precision = 15, scale = 0)
    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "used_count", nullable = false)
    private Integer usedCount = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
    }

    @OneToMany(
            mappedBy = "promotion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PromotionProduct> promotionProducts;
}
