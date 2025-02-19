package com.nhnacademy.inkbridge.backend.dto.coupon;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: OrderCouponReadResponseDto.
 *
 * @author JBum
 * @version 2024/03/07
 */
@Getter
@NoArgsConstructor
public class OrderCouponReadResponseDto {

    private Long bookId;
    private Set<MemberCouponReadResponseDto> memberCouponReadResponseDtos;

    @Builder
    public OrderCouponReadResponseDto(Long bookId,
        Set<MemberCouponReadResponseDto> memberCouponReadResponseDtos) {
        this.bookId = bookId;
        this.memberCouponReadResponseDtos = memberCouponReadResponseDtos;
    }
    
}
