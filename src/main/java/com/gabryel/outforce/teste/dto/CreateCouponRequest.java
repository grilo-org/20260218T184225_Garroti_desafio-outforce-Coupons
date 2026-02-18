package com.gabryel.outforce.teste.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCouponRequest(
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate,
        Boolean published
) {}

