package com.nhnacademy.inkbridge.backend.dto.category;

import lombok.Builder;
import lombok.Getter;

/**
 * class: CategoryNameReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/19
 */
@Getter
public class CategoryNameReadResponseDto {

    private final String categoryName;
    private final String parentCategoryName;

    @Builder
    public CategoryNameReadResponseDto(String categoryName, String parentCategoryName) {
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
    }
}
