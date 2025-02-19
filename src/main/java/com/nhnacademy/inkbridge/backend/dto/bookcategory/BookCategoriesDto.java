package com.nhnacademy.inkbridge.backend.dto.bookcategory;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCategoriesDto.
 *
 * @author JBum
 * @version 2024/03/08
 */
@NoArgsConstructor
@Getter
public class BookCategoriesDto {

    private Long bookId;
    private List<Long> categoryIds;

    @Builder
    public BookCategoriesDto(Long bookId, List<Long> categoryIds) {
        this.bookId = bookId;
        this.categoryIds = categoryIds;
    }
}
