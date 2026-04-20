//package com.laptopshop.models.promotions;
//
//
//import com.laptopshop.models.products.Product;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Table(
//        name = "promotion_products",
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "uq_promo_product",
//                        columnNames = {"promotion_id", "product_id"}
//                )
//        }
//)
//@Entity
//@Getter
//@Setter
//public class PromotionProduct {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "promotion_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_pp_promotion")
//    )
//    private Promotion promotion;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "product_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_pp_product")
//    )
//    private Product product;
//}
