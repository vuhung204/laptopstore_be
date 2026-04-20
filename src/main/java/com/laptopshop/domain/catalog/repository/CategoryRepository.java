package com.laptopshop.domain.catalog.repository;

import com.laptopshop.domain.catalog.entity.Brand;
import com.laptopshop.domain.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByIsActiveTrueAndParentIsNullOrderBySortOrder();
}
