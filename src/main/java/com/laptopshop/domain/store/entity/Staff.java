package com.laptopshop.domain.store.entity;


import com.laptopshop.domain.common.entity.BaseEntity;
import com.laptopshop.domain.support.entity.ReturnRequest;
import com.laptopshop.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "staff")
@Entity
@Getter
@Setter
public class Staff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_staff_store"))
    private Store store;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_staff_role"))
    private Role role;

    @OneToMany(mappedBy = "staff")
    private List<ReturnRequest> processedReturnRequests;

    @OneToMany(mappedBy = "repliedBy")
    private List<Review> repliedReviews;
}
