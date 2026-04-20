package com.laptopshop.application.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    private Long addressId;
    private String note;
    private String paymentMethod;
}
