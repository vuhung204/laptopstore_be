package com.laptopshop.domain.order.repository;

import com.laptopshop.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByOrderedAtDesc(Long userId);
    Optional<Order> findByIdAndUserId(Long id, Long userId);
}
