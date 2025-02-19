package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import java.util.List;

/**
 * class: CategoryService.
 *
 * @author choijaehun
 * @version 2/15/24
 */

public interface CategoryService {

    void createCategory(CategoryCreateRequestDto request);

    CategoryReadResponseDto readCategory(Long categoryId);

    List<ParentCategoryReadResponseDto> readAllCategory();

    CategoryUpdateResponseDto updateCategory(Long categoryId, CategoryUpdateRequestDto request);
}
