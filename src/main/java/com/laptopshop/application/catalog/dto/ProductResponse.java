package com.laptopshop.application.catalog.dto;

import com.laptopshop.domain.catalog.entity.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String sku;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String brandName;
    private String categoryName;
    private String primaryImage;

    private String cpu;
    private String ram;
    private String storage;
    private String display;
    private Double avgRating;
    private Long reviewCount;

    public static ProductResponse from(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.id          = product.getId();
        dto.name        = product.getName();
        dto.slug        = product.getSlug();
        dto.sku         = product.getSku();
        dto.basePrice   = product.getBasePrice();
        dto.salePrice   = product.getSalePrice();
        dto.brandName   = product.getBrand().getName();
        dto.categoryName = product.getCategory().getName();
        dto.primaryImage = product.getImages() != null
                ? product.getImages().stream()
                .filter(i -> i.getIsPrimary())
                .map(i -> i.getImageUrl())
                .findFirst().orElse(null)
                : null;

        // Specs
        if (product.getSpec() != null) {
            dto.cpu     = product.getSpec().getCpu();
            dto.ram     = product.getSpec().getRam();
            dto.storage = product.getSpec().getStorage();
            dto.display = product.getSpec().getDisplay();
        }

        // Rating
        if (product.getReviews() != null && !product.getReviews().isEmpty()) {
            dto.reviewCount = (long) product.getReviews().size();
            dto.avgRating   = product.getReviews().stream()
                    .mapToInt(r -> r.getRating())
                    .average()
                    .orElse(0);
        } else {
            dto.reviewCount = 0L;
            dto.avgRating   = 0.0;
        }

        return dto;
    }
}
