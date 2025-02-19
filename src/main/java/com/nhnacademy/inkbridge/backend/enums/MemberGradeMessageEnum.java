package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: MemberGradeMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */

@Getter
@RequiredArgsConstructor
public enum MemberGradeMessageEnum {
    MEMBER_GRADE_NOT_FOUND_ERROR("회원 등급을 찾지 못했습니다.");
    private final String message;
}
