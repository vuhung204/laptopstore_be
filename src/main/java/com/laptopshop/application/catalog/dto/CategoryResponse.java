package com.laptopshop.application.catalog.dto;

import com.laptopshop.domain.catalog.entity.Category;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private List<CategoryResponse> children;

    public static CategoryResponse from(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.slug = category.getSlug();
        dto.children = category.getChildren() != null
                ? category.getChildren().stream()
                .filter(c -> Boolean.TRUE.equals(c.getIsActive()))
                .map(CategoryResponse::from)
                .collect(Collectors.toList())
                : List.of();
        return dto;
    }
}
