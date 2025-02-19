package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 *  class: BookOrderDetailMessageEnum.
 *
 *  @author minm063
 *  @version 2024/03/20
 */
@Getter
public enum BookOrderDetailMessageEnum {

    BOOK_ORDER_DETAIL_NOT_FOUND("주문 상세를 찾을 수 없습니다.");

    private final String message;

    BookOrderDetailMessageEnum(String message) {
        this.message = message;
    }
}
