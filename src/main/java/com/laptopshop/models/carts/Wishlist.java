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
//        name = "wishlists",
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "uq_wishlist",
//                        columnNames = {"user_id", "product_id"}
//                )
//        }
//)
//@Entity
//@Getter
//@Setter
//public class Wishlist {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "wishlist_id")
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
//    @Column(name = "added_at", updatable = false)
//    private LocalDateTime addedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.addedAt = LocalDateTime.now();
//    }
//}
