package com.laptopshop.application.payment.dto;

import com.laptopshop.domain.order.entity.Payment;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private String method;
    private String status;
    private BigDecimal amount;
    private String paymentUrl;
    private String transactionId;
    private LocalDateTime paidAt;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse dto = new PaymentResponse();
        dto.id = payment.getId();
        dto.orderId = payment.getOrder().getId();
        dto.method = payment.getMethod().name();
        dto.status = payment.getStatus().name();
        dto.amount = payment.getAmount();
        dto.paymentUrl = payment.getPaymentUrl();
        dto.transactionId = payment.getTransactionId();
        dto.paidAt = payment.getPaidAt();
        return dto;
    }
}
