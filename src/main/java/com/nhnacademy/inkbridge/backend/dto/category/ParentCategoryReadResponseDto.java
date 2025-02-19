package com.nhnacademy.inkbridge.backend.dto.category;

import com.nhnacademy.inkbridge.backend.entity.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: ParentCategoryAllReadResponseDto.
 *
 * @author choijaehun
 * @version 2/16/24
 */

@Getter
@NoArgsConstructor
public class ParentCategoryReadResponseDto {

    private Long categoryId;
    private String categoryName;
    private List<ParentCategoryReadResponseDto> parentCategories;

    public ParentCategoryReadResponseDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.parentCategories = category.getCategoryChildren().stream()
            .map(ParentCategoryReadResponseDto::new)
            .collect(Collectors.toList());
    }
}