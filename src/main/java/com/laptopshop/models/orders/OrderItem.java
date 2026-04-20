//package com.laptopshop.models.orders;
//
//
//import com.laptopshop.models.products.Product;
//import com.laptopshop.models.refunds.ReturnRequestItem;
//import com.laptopshop.models.reviews.Review;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Table(
//        name = "order_items",
//        indexes = {
//                @Index(name = "idx_oi_order", columnList = "order_id"),
//                @Index(name = "idx_oi_product", columnList = "product_id")
//        }
//)
//@Entity
//@Getter
//@Setter
//public class OrderItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "item_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "order_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_oi_order")
//    )
//    private Order order;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "product_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_oi_product")
//    )
//    private Product product;
//
//    @Column(nullable = false)
//    private Integer quantity = 1;
//
//    @Column(name = "unit_price", nullable = false, precision = 15, scale = 0)
//    private BigDecimal unitPrice;
//
//    @Column(name = "total_price", nullable = false, precision = 15, scale = 0)
//    private BigDecimal totalPrice;
//
//    @OneToMany(mappedBy = "orderItem")
//    private List<ReturnRequestItem> returnRequestItems;
//
//    @OneToMany(mappedBy = "orderItem")
//    private List<Review> reviews;
//}
