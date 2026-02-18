package com.gabryel.outforce.teste.domain;

import com.gabryel.outforce.teste.exception.BusinessException;

import java.util.Objects;

public record CouponCode(String value) {

    public CouponCode {
        Objects.requireNonNull(value, "code is required");
        String sanitized = sanitize(value);
        if (sanitized.length() != 6) {
            throw new BusinessException("INVALID_CODE_LENGTH", "Coupon code must have 6 alphanumeric characters");
        }
        value = sanitized;
    }

    private static String sanitize(String raw) {
        return raw.replaceAll("[^A-Za-z0-9]", "");
    }
}

