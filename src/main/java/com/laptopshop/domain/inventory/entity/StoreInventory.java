package com.laptopshop.domain.inventory.entity;


import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(
        name = "store_inventory",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_inventory",
                        columnNames = {"store_id", "product_id"}
                )
        },
        indexes = {
                @Index(name = "idx_inventory_store", columnList = "store_id"),
                @Index(name = "idx_inventory_product", columnList = "product_id")
        }
)
@Entity
@Getter
@Setter
public class StoreInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "store_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_inv_store")
    )
    private Store store;

    // Product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_inv_product")
    )
    private Product product;

    @Column(nullable = false)
    private Integer quantity = 0;

    @Column(name = "min_quantity", nullable = false)
    private Integer minQuantity = 5;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
