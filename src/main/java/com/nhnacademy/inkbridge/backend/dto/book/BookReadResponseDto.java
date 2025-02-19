package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.review.ReviewAverageReadResponseDto;
import lombok.Builder;
import lombok.Getter;

/**
 *  class: BookReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class BookReadResponseDto {

    private final BookDetailReadResponseDto bookDetailReadResponseDto;
    private final ReviewAverageReadResponseDto reviewAverageReadResponseDto;

    @Builder
    public BookReadResponseDto(BookDetailReadResponseDto bookDetailReadResponseDto,
        ReviewAverageReadResponseDto reviewAverageReadResponseDto) {
        this.bookDetailReadResponseDto = bookDetailReadResponseDto;
        this.reviewAverageReadResponseDto = reviewAverageReadResponseDto;
    }
}
