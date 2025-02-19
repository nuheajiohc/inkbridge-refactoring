package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: PayMessageEnum.
 *
 * @author jangjaehun
 * @version 2024/03/17
 */
@Getter
@RequiredArgsConstructor
public enum PayMessageEnum {
    PAY_NOT_FOUND("주문정보를 찾지 못했습니다.");

    private final String message;
}
