package com.nhnacademy.inkbridge.backend.dto.cart;

import lombok.Builder;
import lombok.Getter;

/**
 * class: CartReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/12
 */
@Getter
public class CartReadResponseDto {

    private final Integer amount;
    private final Long bookId;

    @Builder
    public CartReadResponseDto(Integer amount, Long bookId) {
        this.amount = amount;
        this.bookId = bookId;
    }
}
