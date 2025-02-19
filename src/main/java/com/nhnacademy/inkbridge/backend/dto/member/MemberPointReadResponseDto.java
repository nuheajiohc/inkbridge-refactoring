package com.nhnacademy.inkbridge.backend.dto.member;

import lombok.Builder;
import lombok.Getter;

/**
 * class: MemberPointReadResponseDto.
 *
 * @author jeongbyeonghun
 * @version 3/19/24
 */
@Getter
public class MemberPointReadResponseDto {

    Long point;

    @Builder
    public MemberPointReadResponseDto(Long point) {
        this.point = point;
    }
}
