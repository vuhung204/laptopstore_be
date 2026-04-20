//package com.laptopshop.models.carts;
//
//import com.laptopshop.models.customers.User;
//import com.laptopshop.models.products.Product;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Table(
//        name = "cart_items",
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "uq_cart",
//                        columnNames = {"user_id", "product_id"}
//                )
//        }
//)
//@Entity
//@Getter
//@Setter
//public class CartItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cart_item_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @Column(nullable = false)
//    private Integer quantity = 1;
//
//    @Column(name = "added_at", updatable = false)
//    private LocalDateTime addedAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        LocalDateTime now = LocalDateTime.now();
//        this.addedAt = now;
//        this.updatedAt = now;
//
//        if (this.quantity == null || this.quantity <= 0) {
//            this.quantity = 1;
//        }
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//}
