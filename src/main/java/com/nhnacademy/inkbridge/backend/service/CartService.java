package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.cart.CartCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.cart.CartReadResponseDto;
import java.util.List;

/**
 * class: CartService.
 *
 * @author minm063
 * @version 2024/03/09
 */
public interface CartService {

    /**
     * memberId에 따른 장바구니를 조회하는 메서드입니다.
     *
     * @param memberId Long
     * @return CartReadResponseDto
     */
    List<CartReadResponseDto> getCart(Long memberId);

    /**
     * 장바구니를 생성하는 메서드입니다.
     *
     * @param cartList CartCreateRequestDto
     */
    void createCart(List<CartCreateRequestDto> cartList);

}
