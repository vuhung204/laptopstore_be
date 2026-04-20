package com.laptopshop.domain.order.repository;

import com.laptopshop.domain.order.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(Long userId);
    Optional<Address> findByIdAndUserId(Long id, Long userId);
    Optional<Address> findByUserIdAndIsDefaultTrue(Long userId);
}
