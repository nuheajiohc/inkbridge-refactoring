package com.nhnacademy.inkbridge.backend.dto.author;

import lombok.Builder;
import lombok.Getter;

/**
 * class: AuthorInfoReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/14
 */
@Getter
public class AuthorInfoReadResponseDto {

    private final Long authorId;
    private final String authorName;
    private final String authorIntroduce;
    private final String fileUrl;

    @Builder
    public AuthorInfoReadResponseDto(Long authorId, String authorName, String authorIntroduce,
        String fileUrl) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorIntroduce = authorIntroduce;
        this.fileUrl = fileUrl;
    }
}
