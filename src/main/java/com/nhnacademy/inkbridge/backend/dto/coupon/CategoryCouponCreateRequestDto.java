package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CouponCreateRequestDTO.
 *
 * @author JBum
 * @version 2024/02/15
 */
@Getter
@NoArgsConstructor
public class CategoryCouponCreateRequestDto {

    @NotNull(message = "쿠폰이름을 지정하지 않았습니다.")
    private String couponName;
    @Column(nullable = false, columnDefinition = "long default 0")
    @NotNull
    private Long minPrice;
    private Long maxDiscountPrice;
    @NotNull(message = "할인 가격을 지정하지 않았습니다")
    private Long discountPrice;
    @NotNull(message = "쿠폰발급시작일을 지정하지 않았습니다.")
    @FutureOrPresent(message = "쿠폰발급시작일이 과거입니다.")
    private LocalDate basicIssuedDate;
    @NotNull(message = "쿠폰발급만료일을 지정하지 않았습니다.")
    @FutureOrPresent(message = "쿠폰발급만료일이 과거입니다.")
    private LocalDate basicExpiredDate;
    @NotNull(message = "쿠폰유효기간을 지정하지 않았습니다")
    @Min(value = 0, message = "쿠폰유효기간이 0일보다 작습니다.")
    private Integer validity;
    @NotNull(message = "쿠폰이 어떤 타입인지 고르지 않았습니다.")
    private Integer couponTypeId;
    @NotNull(message = "카테고리 쿠폰에 카테고리를 선정하지 않았습니다.")
    private Long categoryId;

    @Builder
    public CategoryCouponCreateRequestDto(String couponName, Long minPrice, Long maxDiscountPrice,
        Long discountPrice, LocalDate basicIssuedDate, LocalDate basicExpiredDate, Integer validity,
        Integer couponTypeId, Long categoryId) {
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountPrice = discountPrice;
        this.basicIssuedDate = basicIssuedDate;
        this.basicExpiredDate = basicExpiredDate;
        this.validity = validity;
        this.couponTypeId = couponTypeId;
        this.categoryId = categoryId;
    }
}