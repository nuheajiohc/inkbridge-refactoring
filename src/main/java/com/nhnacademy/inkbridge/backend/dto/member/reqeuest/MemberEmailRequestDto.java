package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: EmailRequestDto.
 *
 * @author devminseo
 * @version 3/12/24
 */
@Getter
@NoArgsConstructor
public class MemberEmailRequestDto {

    @Email(message = "이메일이 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;
}
