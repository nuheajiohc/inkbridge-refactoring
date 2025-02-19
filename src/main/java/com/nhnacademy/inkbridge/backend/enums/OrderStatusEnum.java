package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: OrderStatusEnum.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@RequiredArgsConstructor
@Getter
public enum OrderStatusEnum {

    WAITING(1L),
    SHIPPING(2L),
    DONE(3L),
    RETURN(4L),
    WITHDRAW(5L);

    private final Long orderStatusId;

}
