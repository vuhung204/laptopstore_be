package com.laptopshop.domain.order.repository;

import com.laptopshop.domain.order.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByUserId(Long userId);
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
    void deleteAllByUserId(Long userId);

    @Query("SELECT c FROM CartItem c " +
            "LEFT JOIN FETCH c.product p " +
            "LEFT JOIN FETCH p.brand " +
            "LEFT JOIN FETCH p.spec " +
            "LEFT JOIN FETCH p.images " +
            "WHERE c.user.id = :userId")
    List<CartItem> findAllByUserIdWithDetails(@Param("userId") Long userId);
}
