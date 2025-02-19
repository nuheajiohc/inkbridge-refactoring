package com.nhnacademy.inkbridge.backend.dto.coupon;

import com.nhnacademy.inkbridge.backend.entity.CouponStatus;
import com.nhnacademy.inkbridge.backend.entity.CouponType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

/**
 * class: CouponListResponseDto.
 *
 * @author JBum
 * @version 2024/02/22
 */
@Getter
public class CouponReadResponseDto {

    private String couponId;
    private String couponName;
    private Long minPrice;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private String basicIssuedDate;
    private String basicExpiredDate;
    private Integer validity;
    private Integer couponTypeId;
    private Boolean isBirth;
    private Integer couponStatusId;

    public CouponReadResponseDto(String couponId, String couponName, Long minPrice,
        Long discountPrice,
        Long maxDiscountPrice, LocalDate basicIssuedDate, LocalDate basicExpiredDate,
        Integer validity,
        CouponType couponType, Boolean isBirth, CouponStatus couponStatus) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.basicIssuedDate = basicIssuedDate
            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.basicExpiredDate = basicExpiredDate
            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.validity = validity;
        this.couponTypeId = couponType.getCouponTypeId();
        this.isBirth = isBirth;
        this.couponStatusId = couponStatus.getCouponStatusId();
    }
}
