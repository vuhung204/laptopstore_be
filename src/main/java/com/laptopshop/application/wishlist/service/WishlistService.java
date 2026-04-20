package com.laptopshop.application.wishlist.service;

import com.laptopshop.application.wishlist.dto.WishlistResponse;
import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.catalog.repository.ProductRepository;
import com.laptopshop.domain.order.entity.Wishlist;
import com.laptopshop.domain.order.repository.WishlistRepository;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<WishlistResponse> getWishlist(Long userId) {
        return wishlistRepository.findAllByUserId(userId)
                .stream()
                .map(WishlistResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public WishlistResponse addToWishlist(Long userId, Long productId) {
        // Kiểm tra đã có trong wishlist chưa
        if (wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new RuntimeException("Sản phẩm đã có trong danh sách yêu thích");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Product product = productRepository.findByIdAndIsActiveTrue(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setAddedAt(LocalDateTime.now());

        wishlistRepository.save(wishlist);
        return WishlistResponse.from(wishlist);
    }

    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        if (!wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new RuntimeException("Sản phẩm không có trong danh sách yêu thích");
        }
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
