package com.nhnacademy.inkbridge.backend.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CategoryReadResponseDto.
 *
 * @author choijaehun
 * @version 2/15/24
 */

@Getter
@NoArgsConstructor
public class CategoryReadResponseDto {

    private Long categoryId;
    private String categoryName;

    @Builder
    public CategoryReadResponseDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
