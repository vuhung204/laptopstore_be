package com.laptopshop.interfaces.rest;

import com.laptopshop.application.wishlist.dto.WishlistResponse;
import com.laptopshop.application.wishlist.service.WishlistService;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"))
                .getId();
    }

    // GET /api/wishlist
    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getWishlist(Authentication auth) {
        return ResponseEntity.ok(wishlistService.getWishlist(getUserId(auth)));
    }

    // POST /api/wishlist/{productId}
    @PostMapping("/{productId}")
    public ResponseEntity<WishlistResponse> addToWishlist(
            Authentication auth,
            @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.addToWishlist(getUserId(auth), productId));
    }

    // DELETE /api/wishlist/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
            Authentication auth,
            @PathVariable Long productId) {
        wishlistService.removeFromWishlist(getUserId(auth), productId);
        return ResponseEntity.noContent().build();
    }
}
