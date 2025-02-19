package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoriesDto;
import com.nhnacademy.inkbridge.backend.dto.coupon.MemberCouponReadResponseDto;
import java.util.Set;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: TagCustomRepository.
 *
 * @author JBum
 * @version 2024/03/08
 */

@NoRepositoryBean
public interface MemberCouponCustomRepository {

    /**
     * 주문시 각 책에 맞는 쿠폰 리스트를 찾아주는 메소드.
     *
     * @param memberId          쿠폰 소유자의 id
     * @param bookCategoriesDto 책과 책의 카테고리
     * @return 쿠폰소유자의 각 책에 맞는 쿠폰리스트
     */
    Set<MemberCouponReadResponseDto> findOrderCoupons(Long memberId,
        BookCategoriesDto bookCategoriesDto);
}
