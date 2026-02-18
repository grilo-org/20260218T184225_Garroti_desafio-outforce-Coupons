package com.gabryel.outforce.teste.dto;

import com.gabryel.outforce.teste.domain.Coupon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CouponResponse(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate,
        boolean published
) {
    public static CouponResponse from(Coupon c) {
        return new CouponResponse(
                c.getId(),
                c.getCode().value(),
                c.getDescription(),
                c.getDiscountValue().value(),
                c.getExpirationDate(),
                c.isPublished()
        );
    }
}

