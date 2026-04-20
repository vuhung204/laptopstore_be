package com.laptopshop.application.cart.service;

import com.laptopshop.application.cart.dto.CreateOrderRequest;
import com.laptopshop.application.cart.dto.OrderResponse;
import com.laptopshop.domain.order.entity.Address;
import com.laptopshop.domain.order.entity.CartItem;
import com.laptopshop.domain.order.entity.Order;
import com.laptopshop.domain.order.entity.OrderItem;
import com.laptopshop.domain.order.enums.OrderStatus;
import com.laptopshop.domain.order.repository.AddressRepository;
import com.laptopshop.domain.order.repository.CartItemRepository;
import com.laptopshop.domain.order.repository.OrderRepository;
import com.laptopshop.domain.user.entity.User;
import com.laptopshop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        // Lấy địa chỉ
        Address address = addressRepository.findByIdAndUserId(request.getAddressId(), userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));

        // Lấy giỏ hàng
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        // Tạo order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            BigDecimal price = cartItem.getProduct().getSalePrice() != null
                    ? cartItem.getProduct().getSalePrice()
                    : cartItem.getProduct().getBasePrice();
            item.setUnitPrice(price);
            item.setTotalPrice(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            return item;
        }).collect(Collectors.toList());

        // Tính tiền
        BigDecimal subtotal = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal shippingFee = new BigDecimal("30000");
        BigDecimal totalAmount = subtotal.add(shippingFee);

        // Tạo order
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setOrderCode(generateOrderCode());
        order.setStatus(OrderStatus.PENDING);
        order.setSubtotal(subtotal);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setShippingFee(shippingFee);
        order.setTotalAmount(totalAmount);
        order.setNote(request.getNote());

        // Link order items với order
        orderItems.forEach(item -> item.setOrder(order));
        order.setItems(orderItems);

        orderRepository.save(order);

        // Xóa giỏ hàng sau khi đặt
        cartItemRepository.deleteAllByUserId(userId);

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getOrders(Long userId) {
        return orderRepository.findAllByUserIdOrderByOrderedAtDesc(userId)
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderDetail(Long userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .map(OrderResponse::from)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
    }

    @Transactional
    public OrderResponse cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Chỉ có thể hủy đơn hàng ở trạng thái chờ xác nhận");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        return OrderResponse.from(order);
    }

    private String generateOrderCode() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = String.valueOf((int)(Math.random() * 9000) + 1000);
        return "ORD-" + date + "-" + random;
    }
}
