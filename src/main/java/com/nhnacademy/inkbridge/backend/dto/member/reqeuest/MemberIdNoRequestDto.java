package com.nhnacademy.inkbridge.backend.dto.member.reqeuest;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: MemberIdNoRequestDto.
 *
 * @author devminseo
 * @version 3/12/24
 */
@Getter
@NoArgsConstructor
public class MemberIdNoRequestDto {

    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    private String id;
}
