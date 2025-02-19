package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberUpdatePasswordRequestDto.
 *
 * @author devminseo
 * @version 3/22/24
 */
@Getter
@NoArgsConstructor
public class MemberUpdatePasswordRequestDto {
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String newPassword;
}
