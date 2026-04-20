package com.laptopshop.interfaces.rest;

import com.laptopshop.application.cart.dto.CartItemRequest;
import com.laptopshop.application.cart.dto.CartResponse;
import com.laptopshop.application.cart.service.CartService;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"))
                .getId();
    }

    // GET /api/cart
    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication auth) {
        return ResponseEntity.ok(cartService.getCart(getUserId(auth)));
    }

    // POST /api/cart
    @PostMapping
    public ResponseEntity<CartResponse> addToCart(
            Authentication auth,
            @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(getUserId(auth), request));
    }

    // PUT /api/cart/{productId}?quantity=2
    @PutMapping("/{productId}")
    public ResponseEntity<CartResponse> updateQuantity(
            Authentication auth,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(getUserId(auth), productId, quantity));
    }

    // DELETE /api/cart/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(
            Authentication auth,
            @PathVariable Long productId) {
        cartService.removeItem(getUserId(auth), productId);
        return ResponseEntity.noContent().build();
    }

    // DELETE /api/cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart(Authentication auth) {
        cartService.clearCart(getUserId(auth));
        return ResponseEntity.noContent().build();
    }
}
