package com.nhnacademy.inkbridge.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: OrderCreateResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/11
 */
@AllArgsConstructor
@Getter
public class OrderCreateResponseDto {

    private Long orderId;
    private String orderCode;
}
