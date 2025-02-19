package com.nhnacademy.inkbridge.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * class: OrderPayInfoReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/12
 */
@Getter
@AllArgsConstructor
@ToString
public class OrderPayInfoReadResponseDto {

    private String orderCode;
    private String orderName;
    private Long amount;
}
