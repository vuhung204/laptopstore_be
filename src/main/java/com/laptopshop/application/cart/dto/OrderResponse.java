package com.laptopshop.application.cart.dto;

import com.laptopshop.domain.order.entity.Order;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private String note;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.id = order.getId();
        dto.orderCode = order.getOrderCode();
        dto.status = order.getStatus().name();
        dto.subtotal = order.getSubtotal();
        dto.discountAmount = order.getDiscountAmount();
        dto.shippingFee = order.getShippingFee();
        dto.totalAmount = order.getTotalAmount();
        dto.note = order.getNote();
        dto.createdAt = order.getOrderedAt();
        dto.items = order.getItems() != null
                ? order.getItems().stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toList())
                : List.of();
        return dto;
    }
}
