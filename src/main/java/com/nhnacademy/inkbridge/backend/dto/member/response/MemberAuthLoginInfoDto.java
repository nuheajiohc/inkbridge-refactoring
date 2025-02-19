package com.nhnacademy.inkbridge.backend.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberAuthLoginInfoDto.
 *
 * @author devminseo
 * @version 3/2/24
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthLoginInfoDto {
    private Long memberId;
    private String email;
    private String password;
    private String memberAuthName;
}
