package com.laptopshop.application.cart.service;

import com.laptopshop.application.cart.dto.CartItemRequest;
import com.laptopshop.application.cart.dto.CartItemResponse;
import com.laptopshop.application.cart.dto.CartResponse;
import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.catalog.repository.ProductRepository;
import com.laptopshop.domain.order.entity.CartItem;
import com.laptopshop.domain.order.repository.CartItemRepository;
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
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartResponse getCart(Long userId) {
        List<CartItemResponse> items = cartItemRepository.findAllByUserId(userId)
                .stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
        return CartResponse.from(items);
    }

    @Transactional
    public CartResponse addToCart(Long userId, CartItemRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        Product product = productRepository.findByIdAndIsActiveTrue(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        CartItem item = cartItemRepository
                .findByUserIdAndProductId(userId, request.getProductId())
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setAddedAt(LocalDateTime.now());
        } else {
            // Cộng dồn nếu đã có trong giỏ
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }

        item.setUpdatedAt(LocalDateTime.now());
        cartItemRepository.save(item);

        return getCart(userId);
    }

    @Transactional
    public CartResponse updateQuantity(Long userId, Long productId, Integer quantity) {
        CartItem item = cartItemRepository
                .findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ"));

        item.setQuantity(quantity);
        item.setUpdatedAt(LocalDateTime.now());
        cartItemRepository.save(item);

        return getCart(userId);
    }

    @Transactional
    public void removeItem(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteAllByUserId(userId);
    }
}
