package com.gabryel.outforce.teste.repository;

import com.gabryel.outforce.teste.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {
    List<CouponEntity> findAllByDeletedAtIsNull();
    Optional<CouponEntity> findByIdAndDeletedAtIsNull(UUID id);
}
