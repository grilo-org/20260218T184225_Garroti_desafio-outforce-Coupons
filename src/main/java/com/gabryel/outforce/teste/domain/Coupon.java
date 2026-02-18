package com.gabryel.outforce.teste.domain;

import com.gabryel.outforce.teste.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class Coupon {

    private final UUID id;
    private final CouponCode code;
    private final String description;
    private final DiscountValue discountValue;
    private final LocalDate expirationDate;
    private final boolean published;

    private OffsetDateTime deletedAt;

    private Coupon(UUID id, CouponCode code, String description,
                   DiscountValue discountValue, LocalDate expirationDate,
                   boolean published, OffsetDateTime deletedAt) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
        this.deletedAt = deletedAt;
    }

    public static Coupon create(String rawCode, String description,
                                DiscountValue discountValue, LocalDate expirationDate,
                                boolean published, LocalDate today) {
        if (description == null || description.isBlank())
            throw new BusinessException("DESCRIPTION_REQUIRED", "Description is required");

        if (expirationDate == null)
            throw new BusinessException("EXPIRATION_REQUIRED", "Expiration date is required");

        if (expirationDate.isBefore(today))
            throw new BusinessException("EXPIRATION_IN_PAST", "Expiration date cannot be in the past");

        return new Coupon(
                UUID.randomUUID(),
                new CouponCode(rawCode),
                description.trim(),
                discountValue,
                expirationDate,
                published,
                null
        );
    }

    public static Coupon restore(UUID id, CouponCode code, String description,
                                 DiscountValue discountValue, LocalDate expirationDate,
                                 boolean published, OffsetDateTime deletedAt) {
        return new Coupon(
                id,
                code,
                description,
                discountValue,
                expirationDate,
                published,
                deletedAt
        );
    }

    public void softDelete() {
        if (this.deletedAt != null) {
            throw new BusinessException("ALREADY_DELETED", "Coupon is already deleted");
        }
        this.deletedAt = OffsetDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}

