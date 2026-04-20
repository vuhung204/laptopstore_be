package com.laptopshop.domain.catalog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_category_slug", columnNames = "slug")
        }
)
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_id",
            foreignKey = @ForeignKey(name = "fk_category_parent")
    )
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
