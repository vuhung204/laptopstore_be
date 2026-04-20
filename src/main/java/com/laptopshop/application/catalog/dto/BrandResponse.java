package com.laptopshop.application.catalog.dto;

import com.laptopshop.domain.catalog.entity.Brand;
import lombok.Getter;

@Getter
public class BrandResponse {
    private Long id;
    private String name;
    private String logoUrl;

    public static BrandResponse from(Brand brand) {
        BrandResponse dto = new BrandResponse();
        dto.id = brand.getId();
        dto.name = brand.getName();
        dto.logoUrl = brand.getLogoUrl();
        return dto;
    }
}
