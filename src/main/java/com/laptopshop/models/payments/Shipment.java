//package com.laptopshop.models.payments;
//
//import com.laptopshop.models.orders.Order;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//
//@Table(
//        name = "shipments",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "uq_shipment_order", columnNames = "order_id")
//        }
//)
//@Entity
//@Getter
//@Setter
//public class Shipment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "shipment_id")
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//
//    @Column(nullable = false, length = 100)
//    private String carrier;
//
//    @Column(name = "tracking_code", length = 100)
//    private String trackingCode;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private ShipmentStatus status = ShipmentStatus.PREPARING;
//
//    @Column(columnDefinition = "TEXT")
//    private String note;
//
//    @Column(name = "shipped_at")
//    private LocalDateTime shippedAt;
//
//    @Column(name = "delivered_at")
//    private LocalDateTime deliveredAt;
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
//            this.status = ShipmentStatus.PREPARING;
//        }
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//}
