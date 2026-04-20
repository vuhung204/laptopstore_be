package com.laptopshop.application.wishlist.dto;

import com.laptopshop.domain.order.entity.Wishlist;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class WishlistResponse {
    private Long productId;
    private String productName;
    private String slug;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String primaryImage;
    private String brandName;
    private LocalDateTime addedAt;

    public static WishlistResponse from(Wishlist wishlist) {
        WishlistResponse dto = new WishlistResponse();
        dto.productId = wishlist.getProduct().getId();
        dto.productName = wishlist.getProduct().getName();
        dto.slug = wishlist.getProduct().getSlug();
        dto.basePrice = wishlist.getProduct().getBasePrice();
        dto.salePrice = wishlist.getProduct().getSalePrice();
        dto.brandName = wishlist.getProduct().getBrand().getName();
        dto.primaryImage = wishlist.getProduct().getImages() != null
                ? wishlist.getProduct().getImages().stream()
                .filter(i -> Boolean.TRUE.equals(i.getIsPrimary()))
                .map(i -> i.getImageUrl())
                .findFirst().orElse(null)
                : null;
        dto.addedAt = wishlist.getAddedAt();
        return dto;
    }
}
