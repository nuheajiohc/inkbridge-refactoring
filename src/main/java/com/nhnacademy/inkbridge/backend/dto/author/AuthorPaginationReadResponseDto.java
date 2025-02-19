package com.nhnacademy.inkbridge.backend.dto.author;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * class: AuthorPaginationReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/13
 */
@Getter
public class AuthorPaginationReadResponseDto {

    private final List<String> authorName;

    @Builder
    public AuthorPaginationReadResponseDto(List<String> authorName) {
        this.authorName = authorName;
    }
}
