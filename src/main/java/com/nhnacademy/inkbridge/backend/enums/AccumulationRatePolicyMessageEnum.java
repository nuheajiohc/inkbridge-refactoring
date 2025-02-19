package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: AccumulationRatePolicyMessageEnum.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@RequiredArgsConstructor
@Getter
public enum AccumulationRatePolicyMessageEnum {

    ACCUMULATION_RATE_POLICY_NOT_FOUND("해당 적립율 정책이 존재하지 않습니다."),
    ACCUMULATION_RATE_POLICY_VALID_FAIL("적립율 정책의 유효성 검사에 실패 했습니다.");

    private final String message;
}
