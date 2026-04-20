//package com.laptopshop.models.stores;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Table(name = "roles")
//@Entity
//@Getter
//@Setter
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "store_id", nullable = false)
//    private Long id;
//
//    @Column(name = "name", unique = true, nullable = false)
//    private String name;
//
//    @Column(name = "permissions", columnDefinition = "JSON")
//    private String permissions;
//
//    @OneToMany(mappedBy = "roles")
//    private List<Staff> staffs;
//}
