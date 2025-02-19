package com.nhnacademy.inkbridge.backend.dto.bookcategory;

import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCategoryReadResponseDto.
 *
 * @author choijaehun
 * @version 2/17/24
 */

@NoArgsConstructor
@Getter
public class BookCategoryReadResponseDto {

    private Long categoryId;
    private Long bookId;

    public BookCategoryReadResponseDto(BookCategory bookCategory) {
        this.categoryId = bookCategory.getPk().getCategoryId();
        this.bookId = bookCategory.getPk().getBookId();
    }
}
