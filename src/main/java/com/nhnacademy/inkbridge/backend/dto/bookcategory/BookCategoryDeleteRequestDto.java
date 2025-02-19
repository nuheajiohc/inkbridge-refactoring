package com.nhnacademy.inkbridge.backend.dto.bookcategory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookCategoryDeleteRequestDto.
 *
 * @author choijaehun
 * @version 2/19/24
 */

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class BookCategoryDeleteRequestDto {
    private Long categoryId;
}
