package com.laptopshop.domain.catalog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(
        name = "product_images",
        indexes = {
                @Index(name = "idx_img_product", columnList = "product_id")
        }
)
@Entity
@Getter
@Setter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_img_product")
    )
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}
