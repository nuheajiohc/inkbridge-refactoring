package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: TagMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
public enum TagMessageEnum {
    TAG_NOT_FOUND("태그를 찾을 수 없습니다."), TAG_ALREADY_EXIST("태그가 이미 존재합니다."), TAG_TYPE_VALID_FAIL("태그 형식이 잘못 되었습니다.");
    private final String message;

    TagMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
