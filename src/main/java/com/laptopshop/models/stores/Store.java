//package com.laptopshop.models.stores;
//
//import com.laptopshop.models.BaseEntity;
//import com.laptopshop.models.histories.InventoryTransaction;
//import com.laptopshop.models.inventory.StoreInventory;
//import com.laptopshop.models.orders.Order;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Table(name = "stores")
//@Entity
//@Getter
//@Setter
//public class Store extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "store_id", nullable = false)
//    private Long id;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "address", nullable = false)
//    private String address;
//
//    @Column(name = "district", nullable = false)
//    private String district;
//
//    @Column(name = "city", nullable = false)
//    private String city;
//
//    @Column(name = "phone", nullable = false)
//    private String phone;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "latitude",precision = 10, scale = 7)
//    private BigDecimal latitude;
//
//    @Column(name = "longitude",precision = 10, scale = 7)
//    private BigDecimal longitude;
//
//    @Column(name = "is_active", nullable = false)
//    private Boolean isActive = true;
//
//    @OneToMany(mappedBy = "stores")
//    private List<Staff> staffs;
//
//    @OneToMany(mappedBy = "store")
//    private List<StoreInventory> inventories;
//
//    @OneToMany(mappedBy = "store")
//    private List<Order> orders;
//
//    @OneToMany(mappedBy = "store")
//    private List<InventoryTransaction> inventoryTransactions;
//}
