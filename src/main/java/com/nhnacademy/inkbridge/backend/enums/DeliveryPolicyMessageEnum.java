package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: DeliveryPolicyMessageEnum.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@Getter
@RequiredArgsConstructor
public enum DeliveryPolicyMessageEnum {
    DELIVERY_VALID_FAIL("유효성 검사에 실패했습니다."),
    DELIVERY_POLICY_NOT_FOUND("배송비 정책을 찾지 못했습니다.");

    private final String message;
}
