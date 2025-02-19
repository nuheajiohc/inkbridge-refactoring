package com.nhnacademy.inkbridge.backend.dto.author;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: AuthorInfoReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/15
 */
@Getter
@NoArgsConstructor
public class AuthorCreateUpdateRequestDto {

    @NotBlank
    @Size(max = 20)
    private String authorName;

    @NotBlank
    @Size(max = 100)
    private String authorIntroduce;
}
