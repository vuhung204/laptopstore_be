//package com.laptopshop.models.payments;
//
//
//import com.laptopshop.models.orders.Order;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Table(
//        name = "payments",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "uq_payment_order", columnNames = "order_id")
//        },
//        indexes = {
//                @Index(name = "idx_payment_status", columnList = "status")
//        }
//)
//@Entity
//@Getter
//@Setter
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id")
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private PaymentMethod method;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private PaymentStatus status = PaymentStatus.PENDING;
//
//    @Column(nullable = false, precision = 15, scale = 0)
//    private BigDecimal amount;
//
//    @Column(name = "transaction_id", length = 200)
//    private String transactionId;
//
//    @Column(name = "gateway_data", columnDefinition = "JSON")
//    private String gatewayData;
//
//    @Column(name = "paid_at")
//    private LocalDateTime paidAt;
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        LocalDateTime now = LocalDateTime.now();
//        this.createdAt = now;
//        this.updatedAt = now;
//
//        if (this.status == null) {
//            this.status = PaymentStatus.PENDING;
//        }
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//}
