package com.laptopshop.domain.support.entity;

import com.laptopshop.domain.support.enums.ReturnStatus;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.order.entity.Order;
import com.laptopshop.domain.store.entity.Staff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Table(
        name = "return_requests",
        indexes = {
                @Index(name = "idx_return_order", columnList = "order_id"),
                @Index(name = "idx_return_user", columnList = "user_id"),
                @Index(name = "idx_return_status", columnList = "status")
        }
)
@Entity
@Getter
@Setter
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Long id;

    // ================= RELATION =================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private Staff staff;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnStatus status = ReturnStatus.PENDING;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(name = "staff_note", columnDefinition = "TEXT")
    private String staffNote;

    @Column(name = "refund_amount", precision = 15, scale = 0)
    private BigDecimal refundAmount;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @PrePersist
    protected void onCreate() {
        this.requestedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ReturnStatus.PENDING;
        }
    }

    @OneToMany(
            mappedBy = "returnRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReturnRequestItem> items;
}