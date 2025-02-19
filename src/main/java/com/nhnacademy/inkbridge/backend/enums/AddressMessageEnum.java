package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: AddressMessageEnum.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */
@RequiredArgsConstructor
@Getter
public enum AddressMessageEnum {
    ADDRESS_NOT_FOUND_ERROR("주소를 찾지 못했습니다.") , ADDRESS_VALID_FAIL("주소 형식이 잘못 되었습니다.");

    private final String message;
}
