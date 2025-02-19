package com.nhnacademy.inkbridge.backend.dto.coupon;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BirthDayCouponCreateRequestDto.
 *
 * @author JBum
 * @version 2024/03/24
 */
@Getter
public class BirthDayCouponCreateRequestDto {

    @NotNull(message = "쿠폰이름을 지정하지 않았습니다.")
    private String couponName;
    @Column(nullable = false, columnDefinition = "long default 0")
    @NotNull
    private Long minPrice;
    private Long maxDiscountPrice;
    @NotNull(message = "할인 가격을 지정하지 않았습니다")
    private Long discountPrice;
    @NotNull(message = "생일을 골라주세요")
    @Min(value = 1, message = "1월이상의 값을 입력해주세요.")
    @Max(value = 12, message = "12월이하의 값을 입력해주세요.")
    private Integer month;

    @Builder
    public BirthDayCouponCreateRequestDto(String couponName, Long minPrice, Long maxDiscountPrice,
        Long discountPrice, Integer month) {
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.discountPrice = discountPrice;
        this.month = month;
    }
}
