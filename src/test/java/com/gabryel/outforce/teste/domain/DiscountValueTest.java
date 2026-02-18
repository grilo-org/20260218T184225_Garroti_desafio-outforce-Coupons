package com.gabryel.outforce.teste.domain;

import com.gabryel.outforce.teste.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountValueTest {

    @Test
    void shouldAcceptMinimumValue() {
        var dv = new DiscountValue(new BigDecimal("0.5"));
        assertEquals(new BigDecimal("0.5"), dv.value());
    }

    @Test
    void shouldAcceptGreaterThanMinimum() {
        var dv = new DiscountValue(new BigDecimal("10.50"));
        assertEquals(new BigDecimal("10.50"), dv.value());
    }

    @Test
    void shouldThrowWhenLessThanMinimum() {
        BusinessException ex = assertThrows(BusinessException.class,
                () -> new DiscountValue(new BigDecimal("0.49")));
        assertEquals("INVALID_DISCOUNT", ex.getCode());
    }

    @Test
    void shouldThrowWhenNull() {
        assertThrows(NullPointerException.class, () -> new DiscountValue(null));
    }
}

