package com.laptopshop.domain.inventory.entity;

import com.laptopshop.domain.inventory.enums.InventoryTransactionType;
import com.laptopshop.domain.order.entity.Order;
import com.laptopshop.domain.catalog.entity.Product;
import com.laptopshop.domain.support.entity.ReturnRequest;
import com.laptopshop.domain.store.entity.Store;
import com.laptopshop.domain.store.entity.Staff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Table(
        name = "inventory_transactions",
        indexes = {
                @Index(name = "idx_invtxn_store_product", columnList = "store_id, product_id"),
                @Index(name = "idx_invtxn_product", columnList = "product_id"),
                @Index(name = "idx_invtxn_created", columnList = "created_at"),
                @Index(name = "idx_invtxn_batch", columnList = "batch_ref")
        }
)
@Entity
@Getter
@Setter
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counterparty_store_id")
    private Store counterpartyStore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_return_id")
    private ReturnRequest returnRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "quantity_delta", nullable = false)
    private Integer quantityDelta;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private InventoryTransactionType transactionType;

    @Column(name = "batch_ref", length = 36)
    private String batchRef;

    @Column(length = 500)
    private String note;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}