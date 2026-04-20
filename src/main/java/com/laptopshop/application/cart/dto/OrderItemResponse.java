package com.laptopshop.application.cart.dto;

import com.laptopshop.domain.order.entity.OrderItem;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public static OrderItemResponse from(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.productId = item.getProduct().getId();
        dto.productName = item.getProduct().getName();
        dto.quantity = item.getQuantity();
        dto.unitPrice = item.getUnitPrice();
        dto.totalPrice = item.getTotalPrice();
        return dto;
    }
}
