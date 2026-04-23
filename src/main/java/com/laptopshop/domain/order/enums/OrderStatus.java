package com.laptopshop.domain.order.enums;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPING,
    DELIVERED,
    COMPLETED,
    CANCELLED,
    REFUNDED;

    public static OrderStatus fromString(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }

}
