package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.CategoryMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.CategoryService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: CategoryController.
 *
 * @author choijaehun
 * @version 2/15/24
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<HttpStatus> createCategory(
        @Valid @RequestBody CategoryCreateRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(CategoryMessageEnum.CATEGORY_VALID_FAIL.getMessage());
        }
        categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryReadResponseDto> readCategory(@PathVariable Long categoryId) {
        CategoryReadResponseDto categoryReadResponseDto = categoryService.readCategory(categoryId);
        return new ResponseEntity<>(categoryReadResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParentCategoryReadResponseDto>> readAllCategory() {
        List<ParentCategoryReadResponseDto> allCategories = categoryService.readAllCategory();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryUpdateResponseDto> updateCategory(@PathVariable Long categoryId,
        @Valid @RequestBody
        CategoryUpdateRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(CategoryMessageEnum.CATEGORY_VALID_FAIL.getMessage());
        }
        CategoryUpdateResponseDto categoryUpdateResponseDto = categoryService.updateCategory(
            categoryId, request);
        return new ResponseEntity<>(categoryUpdateResponseDto, HttpStatus.OK);
    }
}
