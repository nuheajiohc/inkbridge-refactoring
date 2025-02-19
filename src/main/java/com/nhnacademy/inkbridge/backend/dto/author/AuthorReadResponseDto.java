package com.nhnacademy.inkbridge.backend.dto.author;

import lombok.Builder;
import lombok.Getter;

/**
 * class: AuthorReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/29
 */
@Getter
public class AuthorReadResponseDto {

    private final Long authorId;
    private final String authorName;

    @Builder
    public AuthorReadResponseDto(Long authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }
}
