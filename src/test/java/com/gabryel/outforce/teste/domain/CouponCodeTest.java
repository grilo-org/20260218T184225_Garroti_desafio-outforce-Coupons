package com.gabryel.outforce.teste.domain;

import com.gabryel.outforce.teste.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouponCodeTest {

    @Test
    void shouldRemoveSpecialCharsAndKeep6Chars() {
        var code = new CouponCode("AB-12@C3");
        assertEquals("AB12C3", code.value());
    }

    @Test
    void shouldAcceptAlreadyValidCode() {
        var code = new CouponCode("ABC123");
        assertEquals("ABC123", code.value());
    }

    @Test
    void shouldThrowWhenSanitizedLengthIsNot6() {
        BusinessException ex = assertThrows(BusinessException.class, () -> new CouponCode("A@1"));
        assertEquals("INVALID_CODE_LENGTH", ex.getCode());
    }

    @Test
    void shouldThrowWhenNull() {
        assertThrows(NullPointerException.class, () -> new CouponCode(null));
    }
}

