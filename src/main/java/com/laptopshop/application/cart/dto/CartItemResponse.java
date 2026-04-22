package com.laptopshop.application.cart.dto;

import com.laptopshop.domain.order.entity.CartItem;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CartItemResponse {
    private Long productId;
    private String productName;
    private String brandName;   // thêm mới
    private String image;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String cpu;         // thêm mới
    private String ram;         // thêm mới
    private String storage;     // thêm mới

    public static CartItemResponse from(CartItem item) {
        CartItemResponse dto = new CartItemResponse();
        dto.productId = item.getProduct().getId();
        dto.productName = item.getProduct().getName();

        dto.brandName = item.getProduct().getBrand() != null
                ? item.getProduct().getBrand().getName()
                : null;

        dto.image = item.getProduct().getImages() != null
                ? item.getProduct().getImages().stream()
                .filter(i -> Boolean.TRUE.equals(i.getIsPrimary()))
                .map(i -> i.getImageUrl())
                .findFirst().orElse(null)
                : null;

        dto.unitPrice = item.getProduct().getSalePrice() != null
                ? item.getProduct().getSalePrice()
                : item.getProduct().getBasePrice();

        dto.quantity = item.getQuantity();
        dto.totalPrice = dto.unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

        if (item.getProduct().getSpec() != null) {
            dto.cpu = item.getProduct().getSpec().getCpu();
            dto.ram = item.getProduct().getSpec().getRam();
            dto.storage = item.getProduct().getSpec().getStorage();
        }

        return dto;
    }
}
