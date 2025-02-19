package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;

/**
 * class: AuthorMessageEnum.
 *
 * @author minm063
 * @version 2024/03/15
 */
@Getter
public enum AuthorMessageEnum {
    AUTHOR_NOT_FOUND("존재하지 않는 작가입니다.");

    private final String message;

    AuthorMessageEnum(String message) {
        this.message = message;
    }
}
