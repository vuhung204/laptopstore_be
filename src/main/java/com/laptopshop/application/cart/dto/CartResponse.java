package com.laptopshop.application.cart.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class CartResponse {
    private List<CartItemResponse> items;
    private int totalItems;
    private BigDecimal totalPrice;

    public static CartResponse from(List<CartItemResponse> items) {
        CartResponse dto = new CartResponse();
        dto.items = items;
        dto.totalItems = items.stream()
                .mapToInt(CartItemResponse::getQuantity).sum();
        dto.totalPrice = items.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return dto;
    }
}
