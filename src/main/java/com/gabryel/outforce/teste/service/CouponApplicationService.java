package com.gabryel.outforce.teste.service;

import com.gabryel.outforce.teste.domain.Coupon;
import com.gabryel.outforce.teste.domain.DiscountValue;
import com.gabryel.outforce.teste.entity.CouponMapper;
import com.gabryel.outforce.teste.exception.NotFoundException;
import com.gabryel.outforce.teste.repository.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CouponApplicationService {

    private final CouponRepository repo;

    public CouponApplicationService(CouponRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Coupon create(String code, String description, BigDecimal discountValue,
                         LocalDate expirationDate, boolean published) {
        var coupon = Coupon.create(
                code,
                description,
                new DiscountValue(discountValue),
                expirationDate,
                published,
                LocalDate.now()
        );
        repo.save(CouponMapper.toEntity(coupon));
        return coupon;
    }

    @Transactional
    public void delete(UUID id) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("COUPON_NOT_FOUND", "Coupon not found"));

        var domain = CouponMapper.toDomain(entity);
        domain.softDelete();

        entity.setDeletedAt(domain.getDeletedAt());
        repo.save(entity);
    }

    @Transactional
    public List<Coupon> listAll() {
        return repo.findAllByDeletedAtIsNull()
                .stream()
                .map(CouponMapper::toDomain)
                .toList();
    }
}

