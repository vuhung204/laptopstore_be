package com.laptopshop.application.catalog.dto;

import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.catalog.entity.ProductImage;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String slug;
    private String sku;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String description;
    private String brandName;
    private String categoryName;
    private List<String> images;
    private String cpu;
    private String ram;
    private String storage;
    private String display;
    private String gpu;
    private String os;
    private BigDecimal weightKg;
    private Integer batteryWh;

    public static ProductDetailResponse from(Product product) {
        ProductDetailResponse dto = new ProductDetailResponse();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.slug = product.getSlug();
        dto.sku = product.getSku();
        dto.basePrice = product.getBasePrice();
        dto.salePrice = product.getSalePrice();
        dto.description = product.getDescription();
        dto.brandName = product.getBrand().getName();
        dto.categoryName = product.getCategory().getName();
        dto.images = product.getImages() != null
                ? product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList())
                : List.of();
        if (product.getSpec() != null) {
            dto.cpu = product.getSpec().getCpu();
            dto.ram = product.getSpec().getRam();
            dto.storage = product.getSpec().getStorage();
            dto.display = product.getSpec().getDisplay();
            dto.gpu = product.getSpec().getGpu();
            dto.os = product.getSpec().getOs();
            dto.weightKg = product.getSpec().getWeightKg();
            dto.batteryWh = product.getSpec().getBatteryWh();
        }
        return dto;
    }
}
