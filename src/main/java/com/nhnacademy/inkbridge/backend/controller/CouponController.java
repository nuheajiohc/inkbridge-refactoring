package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.CouponReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.CouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: CouponController.
 *
 * @author JBum
 * @version 2024/02/16
 */
@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }


    /**
     * 발급가능한 전체 쿠폰을 가져오는 메소드.
     *
     * @param pageable page
     * @return 발급가능한 전체 쿠폰
     */
    @GetMapping
    public ResponseEntity<Page<CouponReadResponseDto>> getCoupons(Pageable pageable) {
        Page<CouponReadResponseDto> coupons = couponService.getIssuableCoupons(pageable);
        return ResponseEntity.ok(coupons);
    }

    /**
     * 한가지 쿠폰의 디테일한 설명을 가져오는 메소드.
     *
     * @param couponId 조회할 쿠폰id
     * @return 조회한 쿠폰id의 상세정보
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailReadResponseDto> getCoupon(
        @PathVariable("couponId") String couponId) {
        return ResponseEntity.ok(couponService.getDetailCoupon(couponId));
    }
}
