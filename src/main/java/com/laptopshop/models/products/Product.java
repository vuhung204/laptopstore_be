//package com.laptopshop.models.products;
//
//
//import com.laptopshop.models.BaseEntity;
//import com.laptopshop.models.brands.Brand;
//import com.laptopshop.models.carts.CartItem;
//import com.laptopshop.models.carts.Wishlist;
//import com.laptopshop.models.categories.Category;
//import com.laptopshop.models.histories.InventoryTransaction;
//import com.laptopshop.models.inventory.StoreInventory;
//import com.laptopshop.models.promotions.PromotionProduct;
//import com.laptopshop.models.reviews.Review;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Table(
//        name = "products",
//        uniqueConstraints = {
//                @UniqueConstraint(name = "uq_product_sku", columnNames = "sku"),
//                @UniqueConstraint(name = "uq_product_slug", columnNames = "slug")
//        },
//        indexes = {
//                @Index(name = "idx_product_brand", columnList = "brand_id"),
//                @Index(name = "idx_product_category", columnList = "category_id")
//        }
//)
//@Entity
//@Getter
//@Setter
//public class Product extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "product_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "brand_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_product_brand")
//    )
//    private Brand brand;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "category_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_product_category")
//    )
//    private Category category;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String slug;
//
//    @Column(nullable = false)
//    private String sku;
//
//    @Column(name = "base_price", nullable = false, precision = 15, scale = 0)
//    private BigDecimal basePrice;
//
//    @Column(name = "sale_price", precision = 15, scale = 0)
//    private BigDecimal salePrice;
//
//    @Column(columnDefinition = "LONGTEXT")
//    private String description;
//
//    @Column(name = "is_active", nullable = false)
//    private boolean isActive = true;
//
//    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private ProductSpec spec;
//
//    @OneToMany(
//            mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<ProductImage> images;
//
//    @OneToMany(mappedBy = "product")
//    private List<StoreInventory> inventories;
//
//    @OneToMany(mappedBy = "product")
//    private List<PromotionProduct> promotionProducts;
//
//    @OneToMany(mappedBy = "product")
//    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "product")
//    private List<CartItem> cartItems;
//
//    @OneToMany(mappedBy = "product")
//    private List<Wishlist> wishlists;
//
//    @OneToMany(mappedBy = "product")
//    private List<InventoryTransaction> inventoryTransactions;
//}
