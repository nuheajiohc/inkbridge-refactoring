package com.nhnacademy.inkbridge.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: PublisherMessageEnum.
 *
 * @author choijaehun
 * @version 2024/03/20
 */

@Getter
@RequiredArgsConstructor
public enum PublisherMessageEnum {

    PUBLISHER_VALID_FAIL("출판사 유효성 검사에 실패하였습니다."),
    PUBLISHER_ALREADY_EXIST("이미 존재하는 멤버입니다."),
    PUBLISHER_NOT_FOUND("조재하지 않는 아이디입니다.");

    private final String message;

}
