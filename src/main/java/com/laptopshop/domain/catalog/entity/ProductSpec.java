package com.laptopshop.domain.catalog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(
        name = "product_specs",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_spec_product", columnNames = "product_id")
        }
)
@Entity
@Getter
@Setter
public class ProductSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_spec_product")
    )
    private Product product;

    private String cpu;
    private String ram;
    private String storage;
    private String display;
    private String gpu;
    private String os;

    @Column(name = "weight_kg", precision = 4, scale = 2)
    private BigDecimal weightKg;

    @Column(name = "battery_wh")
    private Integer batteryWh;

    @Column(length = 500)
    private String ports;

    private String color;
}
