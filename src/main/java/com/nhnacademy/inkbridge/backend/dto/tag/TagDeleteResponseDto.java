package com.nhnacademy.inkbridge.backend.dto.tag;

import lombok.Builder;
import lombok.Getter;

/**
 * class: TagDeleteResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
@Getter
public class TagDeleteResponseDto {

    private final String message;

    @Builder
    public TagDeleteResponseDto(String message) {
        this.message = message;
    }
}
