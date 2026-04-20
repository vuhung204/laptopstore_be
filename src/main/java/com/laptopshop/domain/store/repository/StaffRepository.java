package com.laptopshop.domain.store.repository;

import com.laptopshop.domain.store.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    Optional<Staff> findByEmail(String email);
}
