package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BooksReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
@NoArgsConstructor
public class BooksPaginationReadResponseDto {

    private Long bookId;
    private String bookTitle;
    private Long price;
    private String publisherName;
    private String fileUrl;

    @Builder
    public BooksPaginationReadResponseDto(Long bookId, String bookTitle, Long price,
        String publisherName, String fileUrl) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.price = price;
        this.publisherName = publisherName;
        this.fileUrl = fileUrl;
    }
}
