package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: CategoryMessageEnum.
 *
 * @author choijaehun
 * @version 2024/02/29
 */
public enum CategoryMessageEnum {
    CATEGORY_NOT_FOUND("존재하지 않는 카테고리입니다."),

    PARENT_CATEGORY_NOT_FOUND("존재하지 않는 부모 카테고리입니다."),
    CATEGORY_VALID_FAIL("카테고리는 1~10글자여야 합니다.");

    private String message;

    CategoryMessageEnum(String message) {
        this.message =message;
    }

    public String getMessage(){
        return message;
    }
}
