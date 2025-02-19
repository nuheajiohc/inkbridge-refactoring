package com.nhnacademy.inkbridge.backend.dto.tag;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * class: TagUpdateRequestDto.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */

@Getter
@Setter
public class TagUpdateRequestDto {

    @NotBlank
    @Length(max = 10)
    private String tagName;
}
