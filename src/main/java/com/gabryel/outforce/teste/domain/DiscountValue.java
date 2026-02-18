package com.gabryel.outforce.teste.domain;

import com.gabryel.outforce.teste.exception.BusinessException;

import java.math.BigDecimal;
import java.util.Objects;

public record DiscountValue(BigDecimal value) {

    public DiscountValue {
        Objects.requireNonNull(value, "discountValue is required");
        if (value.compareTo(new BigDecimal("0.5")) < 0) {
            throw new BusinessException("INVALID_DISCOUNT", "Discount value must be at least 0.5");
        }
    }
}


