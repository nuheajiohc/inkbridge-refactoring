package com.nhnacademy.inkbridge.backend.dto.member.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberAuthLoginResponseDto.
 *
 * @author devminseo
 * @version 2/27/24
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthLoginResponseDto {
    private Long memberId;
    private String email;
    private String password;
    private List<String> role;
}
