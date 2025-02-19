package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: CategoryServiceImplTest.
 *
 * @author choijaehun
 * @version 2/18/24
 */

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 생성 테스트 - 성공1")
    void When_CreateBookCategory_Expect_Success1(){
        CategoryCreateRequestDto request = new CategoryCreateRequestDto();
        ReflectionTestUtils.setField(request,"categoryName","IT");

        when(categoryRepository.save(any())).thenReturn(Category.create().build());

        categoryService.createCategory(request);
        verify(categoryRepository,times(1)).save(any());
    }

    @Test
    @DisplayName("카테고리 생성 테스트 - 성공2")
    void When_CreateBookCategory_Expect_Success2(){
        CategoryCreateRequestDto request = new CategoryCreateRequestDto();
        ReflectionTestUtils.setField(request,"categoryName","IT");
        ReflectionTestUtils.setField(request,"parentId",1L);

        when(categoryRepository.findById(any())).thenReturn(Optional.of(Category.create().build()));
        when(categoryRepository.save(any())).thenReturn(Category.create().build());

        categoryService.createCategory(request);
        verify(categoryRepository,times(1)).save(any());
    }

    @Test
    @DisplayName("카테고리 조회 테스트 - 성공")
    void When_ReadCategory_Expect_Success() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.create()
            .build()));

        CategoryReadResponseDto response = categoryService.readCategory(categoryId);
        assertNotNull(response);
    }

    @Test
    @DisplayName("카테고리 조회 테스트 - 카테고리 없을 때")
    void When_ReadCategoryByNotFoundCategory_Expect_Exception() {
        Long categoryId = 10L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> categoryService.readCategory(categoryId));
    }

    @Test
    @DisplayName("모든 카테고리 조회 테스트 - 성공")
    void When_ReadAllCategory_Expect_Success() {
        when(categoryRepository.findAllByCategoryParentIsNull()).thenReturn(
            Collections.singletonList(Category.create().build()));

        List<ParentCategoryReadResponseDto> response = categoryService.readAllCategory();

        verify(categoryRepository, times(1)).findAllByCategoryParentIsNull();
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    @Test
    @DisplayName("카테고리 수정 테스트 - 성공")
    void When_UpdateCategory_Expect_Success() {
        Long categoryId = 1L;
        String categoryName = "변경된 카테고리";
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.create()
            .build()));

        CategoryUpdateRequestDto request = mock(CategoryUpdateRequestDto.class);
        when(request.getCategoryName()).thenReturn(categoryName);

        CategoryUpdateResponseDto response = categoryService.updateCategory(categoryId, request);
        assertNotNull(response);
        assertEquals(categoryName, response.getCategoryName());
    }

    @Test
    @DisplayName("카테고리 수정 테스트 - 카테고리 조회 불가능할 때")
    void When_UpdateCategoryByCategoryIdIsNull_Expect_Success(){
        Long categoryId = 10L;
        CategoryUpdateRequestDto request = mock(CategoryUpdateRequestDto.class);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> categoryService.updateCategory(categoryId,request));
    }
}