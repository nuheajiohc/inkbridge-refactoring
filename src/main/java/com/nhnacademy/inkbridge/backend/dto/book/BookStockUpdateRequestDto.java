package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;

/**
 * class: BookStockUpdateRequestDto.
 *
 * @author minm063
 * @version 2024/03/19
 */
@Getter
public class BookStockUpdateRequestDto {

    private final Long bookId;
    private final Integer amount;

    @Builder
    public BookStockUpdateRequestDto(Long bookId, Integer amount) {
        this.bookId = bookId;
        this.amount = amount;
    }
}
