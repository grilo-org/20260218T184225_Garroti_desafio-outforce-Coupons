package com.gabryel.outforce.teste.controller;

import com.gabryel.outforce.teste.dto.CouponResponse;
import com.gabryel.outforce.teste.dto.CreateCouponRequest;
import com.gabryel.outforce.teste.service.CouponApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponApplicationService service;

    public CouponController(CouponApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(@RequestBody CreateCouponRequest req) {
        var coupon = service.create(
                req.code(),
                req.description(),
                req.discountValue(),
                req.expirationDate(),
                req.published() != null && req.published()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(CouponResponse.from(coupon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> listAll() {
        var coupons = service.listAll()
                .stream()
                .map(CouponResponse::from)
                .toList();
        return ResponseEntity.ok(coupons);
    }
}

