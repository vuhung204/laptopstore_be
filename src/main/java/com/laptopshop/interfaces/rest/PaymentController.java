package com.laptopshop.interfaces.rest;

import com.laptopshop.application.payment.dto.PaymentRequest;
import com.laptopshop.application.payment.dto.PaymentResponse;
import com.laptopshop.application.payment.service.PaymentService;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"))
                .getId();
    }

    // POST /api/payment — tạo payment cho đơn hàng
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            Authentication auth,
            @RequestBody PaymentRequest request) throws Exception {
        return ResponseEntity.ok(paymentService.createPayment(getUserId(auth), request));
    }

    // GET /api/payment/order/{orderId} — xem trạng thái payment
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPayment(
            Authentication auth,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(getUserId(auth), orderId));
    }

    // GET /api/payment/vnpay/callback — VNPay redirect về sau khi thanh toán
    @GetMapping("/vnpay/callback")
    public ResponseEntity<String> vnpayCallback(@RequestParam Map<String, String> params) {
        paymentService.handleVNPayCallback(params);
        return ResponseEntity.ok("Thanh toán VNPay thành công");
    }

    // POST /api/payment/momo/callback — MoMo gọi về (IPN)
    @PostMapping("/momo/callback")
    public ResponseEntity<String> momoCallback(@RequestBody Map<String, String> params) {
        paymentService.handleMoMoCallback(params);
        return ResponseEntity.ok("OK");
    }

    // POST /api/payment/momo/notify — MoMo notify
    @PostMapping("/momo/notify")
    public ResponseEntity<String> momoNotify(@RequestBody Map<String, String> params) {
        paymentService.handleMoMoCallback(params);
        return ResponseEntity.ok("OK");
    }
}
