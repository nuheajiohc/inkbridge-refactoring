package com.nhnacademy.inkbridge.backend.dto.pay;

import lombok.Getter;

/**
 * class: PayCancelRequestDto.
 *
 * @author jangjaehun
 * @version 2024/03/23
 */
@Getter
public class PayCancelRequestDto {
    private String orderCode;
    private String status;
    private Long totalAmount;
    private Long balanceAmount;
    private Boolean isPartialCancelable;
}
