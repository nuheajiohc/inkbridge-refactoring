package com.nhnacademy.inkbridge.backend.dto.member.response;

import lombok.Getter;
import lombok.Value;

/**
 * class: MemberEmailResponseDto.
 *
 * @author devminseo
 * @version 3/12/24
 */
@Getter
@Value
public class MemberEmailResponseDto {
    private final String email;
}
