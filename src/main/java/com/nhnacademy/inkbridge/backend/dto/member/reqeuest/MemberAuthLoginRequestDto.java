package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberAuthLoginRequestDto.
 *
 * @author devminseo
 * @version 2/27/24
 */
@Getter
@NoArgsConstructor
public class MemberAuthLoginRequestDto {

    @Email(message = "이메일 형식이 틀렸습니다.")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;
}
