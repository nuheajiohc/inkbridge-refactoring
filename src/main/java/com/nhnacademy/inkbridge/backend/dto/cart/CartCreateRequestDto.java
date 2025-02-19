package com.nhnacademy.inkbridge.backend.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CartCreateRequestDto.
 *
 * @author minm063
 * @version 2024/03/12
 */
@Getter
@NoArgsConstructor
public class CartCreateRequestDto {

    private Long memberId;
    private Long bookId;
    private Integer amount;

    @Builder
    public CartCreateRequestDto(Long memberId, Long bookId, Integer amount) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.amount = amount;
    }
}
