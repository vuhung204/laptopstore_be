package com.laptopshop.application.payment.service;

import com.laptopshop.application.payment.dto.PaymentRequest;
import com.laptopshop.application.payment.dto.PaymentResponse;
import com.laptopshop.domain.order.entity.Order;
import com.laptopshop.domain.order.entity.Payment;
import com.laptopshop.domain.order.enums.OrderStatus;
import com.laptopshop.domain.order.enums.PaymentMethod;
import com.laptopshop.domain.order.enums.PaymentStatus;
import com.laptopshop.domain.order.repository.OrderRepository;
import com.laptopshop.domain.order.repository.PaymentRepository;
import com.laptopshop.infrastructure.payment.MoMoUtils;
import com.laptopshop.infrastructure.payment.VNPayUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final VNPayUtils vnPayUtils;
    private final MoMoUtils moMoUtils;

    @Transactional
    public PaymentResponse createPayment(Long userId, PaymentRequest request) throws Exception {
        Order order = orderRepository.findByIdAndUserId(request.getOrderId(), userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Kiểm tra đã có payment chưa
        if (paymentRepository.findByOrderId(order.getId()).isPresent()) {
            throw new RuntimeException("Đơn hàng đã được thanh toán");
        }

        PaymentMethod method = PaymentMethod.valueOf(request.getMethod().toUpperCase());
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(method);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);

        switch (method) {
            case COD -> {
                // COD không cần redirect
                payment.setStatus(PaymentStatus.PENDING);
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);
            }
            case VNPAY -> {
                String url = vnPayUtils.createPaymentUrl(
                        order.getId(),
                        order.getTotalAmount().longValue(),
                        "Thanh toan don hang " + order.getOrderCode()
                );
                payment.setPaymentUrl(url);
            }
            case MOMO -> {
                String url = moMoUtils.createPaymentUrl(
                        order.getId(),
                        order.getTotalAmount().longValue(),
                        "Thanh toan don hang " + order.getOrderCode()
                );
                payment.setPaymentUrl(url);
            }
            case BANK_TRANSFER -> {
                // Chuyển khoản thủ công, admin xác nhận sau
                payment.setStatus(PaymentStatus.PENDING);
            }
        }

        paymentRepository.save(payment);
        return PaymentResponse.from(payment);
    }

    // VNPay callback
    @Transactional
    public void handleVNPayCallback(Map<String, String> params) {
        if (!vnPayUtils.verifyCallback(params)) {
            throw new RuntimeException("Chữ ký không hợp lệ");
        }

        String vnpTxnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");
        String transactionId = params.get("vnp_TransactionNo");

        Long orderId = Long.parseLong(vnpTxnRef);
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment"));

        payment.setTransactionId(transactionId);
        payment.setRawResponse(params.toString());

        if ("00".equals(responseCode)) {
            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());
            // Cập nhật trạng thái đơn hàng
            Order order = payment.getOrder();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
    }

    // MoMo callback
    @Transactional
    public void handleMoMoCallback(Map<String, String> params) {
        if (!moMoUtils.verifyCallback(params)) {
            throw new RuntimeException("Chữ ký không hợp lệ");
        }

        String orderId_ = params.get("orderId"); // "ORDER_1"
        Long orderId = Long.parseLong(orderId_.replace("ORDER_", ""));
        String resultCode = params.get("resultCode");
        String transId = params.get("transId");

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment"));

        payment.setTransactionId(transId);
        payment.setRawResponse(params.toString());

        if ("0".equals(resultCode)) {
            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());
            Order order = payment.getOrder();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
    }

    public PaymentResponse getPaymentByOrder(Long userId, Long orderId) {
        orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        return paymentRepository.findByOrderId(orderId)
                .map(PaymentResponse::from)
                .orElseThrow(() -> new RuntimeException("Chưa có thông tin thanh toán"));
    }
}
