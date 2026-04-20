package com.laptopshop.interfaces.rest;

import com.laptopshop.application.cart.dto.CreateOrderRequest;
import com.laptopshop.application.cart.dto.OrderResponse;
import com.laptopshop.application.cart.service.OrderService;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"))
                .getId();
    }

    // POST /api/orders
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            Authentication auth,
            @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(getUserId(auth), request));
    }

    // GET /api/orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(Authentication auth) {
        return ResponseEntity.ok(orderService.getOrders(getUserId(auth)));
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderDetail(
            Authentication auth,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderDetail(getUserId(auth), id));
    }

    // PUT /api/orders/{id}/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            Authentication auth,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(getUserId(auth), id));
    }
}
