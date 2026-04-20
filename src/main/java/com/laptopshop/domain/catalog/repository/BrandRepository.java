package com.laptopshop.domain.catalog.repository;

import com.laptopshop.domain.catalog.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAllByIsActiveTrueOrderByName();
}
