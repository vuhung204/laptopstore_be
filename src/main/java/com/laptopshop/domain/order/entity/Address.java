package com.laptopshop.domain.order.entity;


import com.laptopshop.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "addresses")
@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_address_user")
    )
    private User user;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    private String ward;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @OneToMany(mappedBy = "address")
    private List<Order> orders;
}
