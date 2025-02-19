package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import java.util.List;

/**
 * class: CartRepositoryCustom.
 *
 * @author minm063
 * @version 2024/03/12
 */
public interface ShoppingCartRepositoryCustom {

    /**
     * memberId에 따른 장바구니 물품을 조회하는 메서드입니다.
     *
     * @param memberId Long
     * @return CartReadResponseDto
     */
    List<CartReadResponseDto> findByMemberId(Long memberId);
}
