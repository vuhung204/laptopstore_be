package com.laptopshop.application.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private String method;
}
