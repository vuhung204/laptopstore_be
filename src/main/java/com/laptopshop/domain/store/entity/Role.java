package com.laptopshop.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "roles")
@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "permissions", columnDefinition = "JSON")
    private String permissions;

    @OneToMany(mappedBy = "role")
    private List<Staff> staffs;
}
