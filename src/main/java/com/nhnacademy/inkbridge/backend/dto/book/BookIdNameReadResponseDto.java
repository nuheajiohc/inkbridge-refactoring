package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookIdNameReadResponseDto.
 *
 * @author JBum
 * @version 2024/03/11
 */
@Getter
@NoArgsConstructor
public class BookIdNameReadResponseDto {

    private Long bookId;
    private String bookTitle;

    public BookIdNameReadResponseDto(Long bookId, String bookTitle) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
    }
}
