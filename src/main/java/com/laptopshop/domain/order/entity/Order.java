package com.laptopshop.domain.order.entity;


import com.laptopshop.domain.order.enums.OrderStatus;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.support.entity.ReturnRequest;
import com.laptopshop.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table(
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_order_code", columnNames = "order_code")
        },
        indexes = {
                @Index(name = "idx_order_user", columnList = "user_id"),
                @Index(name = "idx_order_store", columnList = "store_id"),
                @Index(name = "idx_order_status", columnList = "status"),
                @Index(name = "idx_order_date", columnList = "ordered_at")
        }
)
@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_store"))
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id",
            foreignKey = @ForeignKey(name = "fk_order_address"))
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id",
            foreignKey = @ForeignKey(name = "fk_order_promotion"))
    private Promotion promotion;

    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false, precision = 15, scale = 0)
    private BigDecimal subtotal;

    @Column(name = "discount_amount", nullable = false, precision = 15, scale = 0)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "shipping_fee", nullable = false, precision = 15, scale = 0)
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 0)
    private BigDecimal totalAmount;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "ordered_at", updatable = false)
    private LocalDateTime orderedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.orderedAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items;

    @OneToMany(mappedBy = "order")
    private List<ReturnRequest> returnRequests;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Shipment shipment;
}
