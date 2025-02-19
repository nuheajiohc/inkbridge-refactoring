package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: MemberMessageEnum.
 *
 * @author JBum
 * @version 2024/02/15
 */
public enum MemberMessageEnum {
    MEMBER_NOT_FOUND("요청하신 맴버를 찾을 수 없습니다."),
    MEMBER_AUTH_NOT_FOUND("요청하신 맴버등급을 찾을 수 없습니다."),
    MEMBER_STATUS_NOT_FOUND("요청하신 멤버상태를 찾을 수 없습니다."),
    MEMBER_ALREADY_EXIST("이미 존재하는 멤버입니다."),
    MEMBER_VALID_FAIL("회원 유효성 검사에 실패했습니다."),

    MEMBER_ID("MemberId: ");

    private final String message;

    MemberMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
