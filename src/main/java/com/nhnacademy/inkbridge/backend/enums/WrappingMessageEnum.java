package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: WrappingMessageEnum.
 *
 * @author JBum
 * @version 2024/03/12
 */
public enum WrappingMessageEnum {
    WRAPPING_NOT_FOUND("존재하지 않는 포장지입니다");

    private final String message;

    WrappingMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
