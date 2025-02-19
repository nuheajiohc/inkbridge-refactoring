package com.nhnacademy.inkbridge.backend.dto.wish;

import lombok.Builder;
import lombok.Getter;

/**
 * class: BookWishReadResponseDto.
 *
 * @author jeongbyeonghun
 * @version 3/25/24
 */
@Getter
public class BookWishReadResponseDto {

    private final Long bookId;
    private final String bookTitle;
    private final String thumbnail;

    @Builder
    public BookWishReadResponseDto(Long bookId, String bookTitle, String thumbnail) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.thumbnail = thumbnail;
    }
}
