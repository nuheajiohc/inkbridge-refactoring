package com.nhnacademy.inkbridge.backend.dto.category;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: CategoryUpdateResponseDto.
 *
 * @author choijaehun
 * @version 2/16/24
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryUpdateResponseDto {

    private String categoryName;

    @Builder
    public CategoryUpdateResponseDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
