package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: MemberPointMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */

@RequiredArgsConstructor
@Getter
public enum MemberPointMessageEnum {
    MEMBER_POINT_VALID_FAIL("포인트 증감값이 올바르지 않습니다.");

    private final String message;
}
