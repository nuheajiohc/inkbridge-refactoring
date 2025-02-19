package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberCouponReadResponseDto.
 *
 * @author JBum
 * @version 2024/03/07
 */
@Getter
@NoArgsConstructor
public class MemberCouponReadResponseDto {

    private Long memberCouponId;
    private LocalDate expiredAt;
    private LocalDate usedAt;
    private String couponName;
    private Long minPrice;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private Integer couponTypeId;
    private String couponTypeName;
    private Boolean isBirth;
    private Integer couponStatusId;
    private String couponStatusName;

    @Builder
    public MemberCouponReadResponseDto(Long memberCouponId, LocalDate expiredAt, LocalDate usedAt,
        String couponName, Long minPrice, Long discountPrice, Long maxDiscountPrice,
        Integer couponTypeId, String couponTypeName, Boolean isBirth, Integer couponStatusId,
        String couponStatusName) {
        this.memberCouponId = memberCouponId;
        this.expiredAt = expiredAt;
        this.usedAt = usedAt;
        this.couponName = couponName;
        this.minPrice = minPrice;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.couponTypeId = couponTypeId;
        this.couponTypeName = couponTypeName;
        this.isBirth = isBirth;
        this.couponStatusId = couponStatusId;
        this.couponStatusName = couponStatusName;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((memberCouponId == null) ? 0 : memberCouponId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MemberCouponReadResponseDto other = (MemberCouponReadResponseDto) obj;
        if (memberCouponId == null) {
            if (other.memberCouponId != null) {
                return false;
            }
        } else if (!memberCouponId.equals(other.memberCouponId)) {
            return false;
        }
        return true;
    }
}
