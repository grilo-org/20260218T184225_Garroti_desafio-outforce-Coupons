package com.gabryel.outforce.teste.entity;

import com.gabryel.outforce.teste.domain.Coupon;
import com.gabryel.outforce.teste.domain.CouponCode;
import com.gabryel.outforce.teste.domain.DiscountValue;

public class CouponMapper {
    public static CouponEntity toEntity(Coupon c) {
        var e = new CouponEntity();
        e.setId(c.getId());
        e.setCode(c.getCode().value());
        e.setDescription(c.getDescription());
        e.setDiscountValue(c.getDiscountValue().value());
        e.setExpirationDate(c.getExpirationDate());
        e.setPublished(c.isPublished());
        e.setDeletedAt(c.getDeletedAt());
        return e;
    }

    public static Coupon toDomain(CouponEntity e) {
        return Coupon.restore(
                e.getId(),
                new CouponCode(e.getCode()),
                e.getDescription(),
                new DiscountValue(e.getDiscountValue()),
                e.getExpirationDate(),
                e.isPublished(),
                e.getDeletedAt()
        );
    }
}

