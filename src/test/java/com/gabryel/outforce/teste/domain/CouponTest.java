package com.gabryel.outforce.teste.domain;

import com.gabryel.outforce.teste.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    void shouldCreateCouponWithSanitizedCode() {
        LocalDate today = LocalDate.of(2026, 2, 17);

        var coupon = Coupon.create(
                "AB-12@C3",
                "Cupom de teste",
                new DiscountValue(new BigDecimal("1.0")),
                today.plusDays(1),
                true,
                today
        );

        assertNotNull(coupon.getId());
        assertEquals("AB12C3", coupon.getCode().value());
        assertEquals("Cupom de teste", coupon.getDescription());
        assertEquals(new BigDecimal("1.0"), coupon.getDiscountValue().value());
        assertEquals(today.plusDays(1), coupon.getExpirationDate());
        assertTrue(coupon.isPublished());
        assertFalse(coupon.isDeleted());
    }

    @Test
    void shouldNotAllowExpirationDateInPast() {
        LocalDate today = LocalDate.of(2026, 2, 17);

        BusinessException ex = assertThrows(BusinessException.class, () ->
                Coupon.create(
                        "ABC123",
                        "desc",
                        new DiscountValue(new BigDecimal("1.0")),
                        today.minusDays(1),
                        false,
                        today
                )
        );

        assertEquals("EXPIRATION_IN_PAST", ex.getCode());
    }

    @Test
    void shouldAllowExpirationDateTodayOrFuture() {
        LocalDate today = LocalDate.of(2026, 2, 17);

        var couponToday = Coupon.create(
                "ABC123",
                "desc",
                new DiscountValue(new BigDecimal("1.0")),
                today,          // hoje permitido
                false,
                today
        );

        assertEquals(today, couponToday.getExpirationDate());
    }

    @Test
    void shouldRequireDescription() {
        LocalDate today = LocalDate.of(2026, 2, 17);

        BusinessException ex = assertThrows(BusinessException.class, () ->
                Coupon.create(
                        "ABC123",
                        "   ",
                        new DiscountValue(new BigDecimal("1.0")),
                        today.plusDays(1),
                        false,
                        today
                )
        );

        assertEquals("DESCRIPTION_REQUIRED", ex.getCode());
    }

    @Test
    void shouldSoftDeleteCoupon() {
        LocalDate today = LocalDate.of(2026, 2, 17);

        var coupon = Coupon.create(
                "ABC123",
                "desc",
                new DiscountValue(new BigDecimal("1.0")),
                today.plusDays(1),
                false,
                today
        );

        coupon.softDelete();

        assertTrue(coupon.isDeleted());
        assertNotNull(coupon.getDeletedAt());
    }

    @Test
    void shouldNotAllowSoftDeleteTwice() {
        LocalDate today = LocalDate.of(2026, 2, 17);

        var coupon = Coupon.create(
                "ABC123",
                "desc",
                new DiscountValue(new BigDecimal("1.0")),
                today.plusDays(1),
                false,
                today
        );

        coupon.softDelete();

        BusinessException ex = assertThrows(BusinessException.class, coupon::softDelete);
        assertEquals("ALREADY_DELETED", ex.getCode());
    }
}
