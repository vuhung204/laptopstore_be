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
        AND (:minPrice IS NULL OR 
             (CASE WHEN p.salePrice IS NOT NULL THEN p.salePrice ELSE p.basePrice END) >= :minPrice)
        AND (:maxPrice IS NULL OR 
             (CASE WHEN p.salePrice IS NOT NULL THEN p.salePrice ELSE p.basePrice END) <= :maxPrice)
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

    // Query 1: images + spec + brand + category
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.images " +
            "LEFT JOIN FETCH p.spec " +
            "LEFT JOIN FETCH p.brand " +
            "LEFT JOIN FETCH p.category " +
            "WHERE p.id = :id AND p.isActive = true")
    Optional<Product> findByIdWithImages(@Param("id") Long id);

    // Query 2: chỉ inventories
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.inventories " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithInventories(@Param("id") Long id);

    // Query 3: chỉ reviews
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.reviews " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithReviews(@Param("id") Long id);
}
