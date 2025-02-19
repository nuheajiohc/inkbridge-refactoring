package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.coupon.CouponDetailReadResponseDto;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: TagCustomRepository.
 *
 * @author JBum
 * @version 2024/03/08
 */

@NoRepositoryBean
public interface CouponCustomRepository {

    /**
     * 쿠폰의 상세한 정보를 찾아주는 메소드.
     *
     * @param couponId 쿠폰ID
     * @return 쿠폰의 상세한 정보
     */
    Optional<CouponDetailReadResponseDto> findDetailCoupon(String couponId);
}
