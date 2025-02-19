package com.nhnacademy.inkbridge.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  class: OrderedMemberReadResponseDto.
 *
 *  @author jangjaehun
 *  @version 2024/03/24
 */
@Getter
@AllArgsConstructor
public class OrderedMemberReadResponseDto {

    private Long memberId;
    private Long totalPrice;
}
