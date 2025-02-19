package com.nhnacademy.inkbridge.backend.dto.bookcategory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCategoryCreateRequestDto.
 *
 * @author choijaehun
 * @version 2/17/24
 */

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BookCategoryCreateRequestDto {
    private Long categoryId;
    private Long bookId;

}
