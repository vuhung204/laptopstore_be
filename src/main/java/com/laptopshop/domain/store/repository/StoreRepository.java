package com.laptopshop.domain.store.repository;

import com.laptopshop.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findFirstByIsActiveTrueOrderByIdAsc();
}
