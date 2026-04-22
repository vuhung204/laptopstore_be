package com.laptopshop.application.catalog.service;

import com.laptopshop.application.catalog.dto.BrandResponse;
import com.laptopshop.application.catalog.dto.CategoryResponse;
import com.laptopshop.application.catalog.dto.ProductDetailResponse;
import com.laptopshop.application.catalog.dto.ProductResponse;
import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.catalog.repository.BrandRepository;
import com.laptopshop.domain.catalog.repository.CategoryRepository;
import com.laptopshop.domain.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductResponse> getProducts(Long brandId, Long categoryId,
                                             BigDecimal minPrice, BigDecimal maxPrice,
                                             String keyword, int page, int size, String sortBy) {

        Sort sort = switch (sortBy) {
            case "price_asc"  -> Sort.by("salePrice").ascending();
            case "price_desc" -> Sort.by("salePrice").descending();
            case "newest"     -> Sort.by("createdAt").descending();
            default           -> Sort.by("id").descending();
        };

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findWithFilters(
                        brandId, categoryId, minPrice, maxPrice, keyword, pageable)
                .map(ProductResponse::from);
    }

    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findByIdWithImages(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        productRepository.findByIdWithInventories(id)
                .ifPresent(p -> product.setInventories(p.getInventories()));

        productRepository.findByIdWithReviews(id)
                .ifPresent(p -> product.setReviews(p.getReviews()));

        return ProductDetailResponse.from(product);
    }

    public List<BrandResponse> getBrands() {
        return brandRepository.findAllByIsActiveTrueOrderByName()
                .stream().map(BrandResponse::from).collect(Collectors.toList());
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAllByIsActiveTrueAndParentIsNullOrderBySortOrder()
                .stream()
                .map(c -> CategoryResponse.from(c))
                .collect(Collectors.toList());
    }
}
