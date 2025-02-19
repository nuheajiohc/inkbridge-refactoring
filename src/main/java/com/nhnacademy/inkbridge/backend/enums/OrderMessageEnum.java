package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: OrderMessageEnum.
 *
 * @author JBum
 * @version 2024/02/28
 */
@Getter
@RequiredArgsConstructor
public enum OrderMessageEnum {
    WRAPPING_NOT_FOUND("포장지가 없습니다."),
    ORDER_NOT_FOUND("주문 정보가 없습니다."),
    ORDER_STATUS_NOT_FOUND("주문 상태가 없습니다."),
    ALREADY_PROCESSED("이미 처리된 주문입니다.");

    private String message;

    OrderMessageEnum(String message) {
        this.message = message;
    }
}