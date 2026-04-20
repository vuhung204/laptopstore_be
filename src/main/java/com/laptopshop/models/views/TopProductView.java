//package com.laptopshop.models.views;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//
//
//@Table(name = "v_top_products")
//@Entity
//@Getter
//@Setter
//public class TopProductView {
//
//    @Id
//    @Column(name = "product_id")
//    private Long productId;
//
//    private String name;
//
//    private String sku;
//
//    private String brand;
//
//    @Column(name = "total_sold")
//    private Long totalSold;
//
//    @Column(name = "total_revenue", precision = 15, scale = 0)
//    private BigDecimal totalRevenue;
//
//    @Column(name = "avg_rating")
//    private Double avgRating;
//
//    @Column(name = "review_count")
//    private Long reviewCount;
//}
