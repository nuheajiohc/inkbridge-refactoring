package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 *  class: BookOrderDetailMessageEnum.
 *
 *  @author minm063
 *  @version 2024/03/20
 */
@Getter
public enum ReviewMessageEnum {

    REVIEW_NOT_FOUND("리뷰를 찾을 수 없습니다.");

    private final String message;

    ReviewMessageEnum(String message) {
        this.message = message;
    }
}
