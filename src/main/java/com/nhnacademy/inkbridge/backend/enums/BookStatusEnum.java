package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 * class: BookStatusEnum.
 *
 * @author minm063
 * @version 2024/03/13
 */
@Getter
public enum BookStatusEnum {
    SALE(1L),
    STOP_SALE(2L),
    SOLD_OUT(3L),
    OUT_OF_STOCK(4L),
    REMOVAL(5L);

    private final Long statusId;

    BookStatusEnum(Long statusId) {
        this.statusId = statusId;
    }
}
