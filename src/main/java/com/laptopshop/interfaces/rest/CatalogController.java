package com.laptopshop.interfaces.rest;

import com.laptopshop.application.catalog.dto.BrandResponse;
import com.laptopshop.application.catalog.dto.CategoryResponse;
import com.laptopshop.application.catalog.dto.ProductDetailResponse;
import com.laptopshop.application.catalog.dto.ProductResponse;
import com.laptopshop.application.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    // GET /api/products?brandId=&categoryId=&minPrice=&maxPrice=&keyword=&page=0&size=12&sort=newest
    @GetMapping("/api/products")
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "newest") String sort) {

        return ResponseEntity.ok(catalogService.getProducts(
                brandId, categoryId, minPrice, maxPrice, keyword, page, size, sort));
    }

    // GET /api/products/{id}
    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getProductDetail(id));
    }

    // GET /api/brands
    @GetMapping("/api/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        return ResponseEntity.ok(catalogService.getBrands());
    }

    // GET /api/categories
    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(catalogService.getCategories());
    }
}
