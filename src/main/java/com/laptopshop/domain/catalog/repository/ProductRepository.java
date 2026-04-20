package com.laptopshop.domain.catalog.repository;

import com.laptopshop.domain.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndIsActiveTrue(Long id);

    @Query("""
        SELECT p FROM Product p
        WHERE p.isActive = true
        AND (:brandId IS NULL OR p.brand.id = :brandId)
        AND (:categoryId IS NULL OR p.category.id = :categoryId)
        AND (:minPrice IS NULL OR p.salePrice >= :minPrice OR p.basePrice >= :minPrice)
        AND (:maxPrice IS NULL OR p.salePrice <= :maxPrice OR p.basePrice <= :maxPrice)
        AND (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<Product> findWithFilters(
            @Param("brandId") Long brandId,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
