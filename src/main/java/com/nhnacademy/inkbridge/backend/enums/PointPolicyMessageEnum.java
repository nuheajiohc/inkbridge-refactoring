package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: PointPolicyMessageEnum.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@RequiredArgsConstructor
@Getter
public enum PointPolicyMessageEnum {

    POINT_POLICY_TYPE_NOT_FOUND("포인트 정책 유형을 찾지 못했습니다."),
    POINT_POLICY_TYPE_ALREADY_EXIST("해당 포인트 정책 유형 이름이 존재합니다."),
    POINT_POLICY_TYPE_VALID_FAIL("포인트 정책 유형의 유효성 검사에 실패했습니다."),
    POINT_POLICY_VALID_FAIL("포인트 정책 유효성 검사에 실패했습니다."),
    POINT_POLICY_NOT_FOUND("포인트 정책을 찾지 못했습니다.");

    private final String message;
}
