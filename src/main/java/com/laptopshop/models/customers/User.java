//package com.laptopshop.models.customers;
//
//import com.laptopshop.models.BaseEntity;
//import com.laptopshop.models.carts.CartItem;
//import com.laptopshop.models.carts.Wishlist;
//import com.laptopshop.models.orders.Order;
//import com.laptopshop.models.refunds.ReturnRequest;
//import com.laptopshop.models.reviews.Review;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
////@Table(
////        name = "users",
////        indexes = {
////                @Index(name = "idx_user_phone", columnList = "phone")
////        })
////@Entity
////@Getter
////@Setter
////public class User extends BaseEntity {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "user_id", nullable = false)
////    private Long id;
////
////    @Column(name = "full_name", nullable = false)
////    private String fullName;
////
////    @Column(name = "email",unique = true, nullable = false)
////    private String email;
////
////    @Column(name = "phone")
////    private String phone;
////
////    @Column(name = "password_hash", nullable = false)
////    private String passwordHash;
////
////    @Column(name = "avatar_url")
////    private String avatarUrl;
////
////    @Enumerated(EnumType.STRING)
////    @Column(name = "status", nullable = false)
////    private UserStatus status;
////
////    @Column(name = "last_login")
////    private LocalDateTime lastLogin;
////
////    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
////    private List<Address> addresses;
////
////    @OneToMany(mappedBy = "user")
////    private List<Order> orders;
////
////    @OneToMany(mappedBy = "user")
////    private List<ReturnRequest> returnRequests;
////
////    @OneToMany(mappedBy = "user")
////    private List<Review> reviews;
////
////    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<CartItem> cartItems;
////
////    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<Wishlist> wishlists;
////}
