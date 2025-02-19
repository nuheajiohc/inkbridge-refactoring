package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryDeleteRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import java.util.ArrayList;
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
 * class: BookCategoryServiceImplTest.
 *
 * @author choijaehun
 * @version 2/19/24
 */

@ExtendWith(MockitoExtension.class)
class BookCategoryServiceImplTest {

    @InjectMocks
    BookCategoryServiceImpl bookCategoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookCategoryRepository bookCategoryRepository;

    @Test
    @DisplayName("book-category 생성 테스트 - 성공")
    void When_CreateBookCategory_Expect_Success() {
        Long categoryId = 1L;
        Long bookId = 1L;

        BookCategoryCreateRequestDto request = new BookCategoryCreateRequestDto();
        ReflectionTestUtils.setField(request, "categoryId", categoryId);
        ReflectionTestUtils.setField(request, "bookId", bookId);

        when(categoryRepository.findById(categoryId)).thenReturn(
            Optional.of(Category.create().build()));
        when(bookRepository.findById(categoryId)).thenReturn(Optional.of(Book.builder().build()));

        bookCategoryService.createBookCategory(request);

        verify(bookCategoryRepository, times(1)).save(any(BookCategory.class));
    }

    @Test
    @DisplayName("book-category 생성 테스트 - 카테고리가 없을 때")
    void When_CreateBookCategoryByCategoryNotFound_Expect_Exception() {
        Long categoryId = 1L;
        Long bookId = 1L;

        BookCategoryCreateRequestDto request = new BookCategoryCreateRequestDto();
        ReflectionTestUtils.setField(request, "categoryId", categoryId);
        ReflectionTestUtils.setField(request, "bookId", bookId);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
            () -> bookCategoryService.createBookCategory(request));
    }

    @Test
    @DisplayName("book-category 생성 테스트 - 책이 없을 때")
    void When_CreateBookCategoryByBookNotFound_Expect_Exception() {
        Long categoryId = 1L;
        Long bookId = 1L;

        BookCategoryCreateRequestDto request = new BookCategoryCreateRequestDto();
        ReflectionTestUtils.setField(request, "categoryId", categoryId);
        ReflectionTestUtils.setField(request, "bookId", bookId);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(Category.create().build()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
            () -> bookCategoryService.createBookCategory(request));
    }

    @Test
    @DisplayName("book-category 조회 테스트 - 성공")
    void When_ReadBookCategory_Expect_Success() {
        BookCategory.Pk pk = BookCategory.Pk.builder().categoryId(1L).bookId(1L).build();

        BookCategory bookCategory = BookCategory.create().book(Book.builder().build())
            .category(Category.create()
                .build()).pk(pk).build();

        List<BookCategory> bookCategories = new ArrayList<>();
        bookCategories.add(bookCategory);

        when(bookCategoryRepository.existsByPk_BookId(1L)).thenReturn(true);
        when(bookCategoryRepository.findByPk_BookId(1L)).thenReturn(bookCategories);

        List<BookCategoryReadResponseDto> result = bookCategoryService.readBookCategory(1L);

        assertNotNull(result);
        assertEquals(bookCategories.size(), result.size());
    }

    @Test
    @DisplayName("book-category 조회 테스트 - bookId가 없을 때")
    void When_ReadBookCategory_Expect_Exception() {
        when(bookCategoryRepository.existsByPk_BookId(anyLong())).thenReturn(false);
        assertThrows(NotFoundException.class, () -> bookCategoryService.readBookCategory(1L));
    }

    @Test
    @DisplayName("book-category 삭제 테스트 - 성공")
    void When_DeleteBookCategory_Expect_Success() {
        Long categoryId = 1L;
        BookCategoryDeleteRequestDto request = new BookCategoryDeleteRequestDto();

        ReflectionTestUtils.setField(request, "categoryId", categoryId);

        when(bookCategoryRepository.existsByPk_BookId(1L)).thenReturn(true);

        bookCategoryService.deleteBookCategory(categoryId, request);

        verify(bookCategoryRepository, times(1)).deleteByPk(any());
    }

    @Test
    @DisplayName("book-category 삭제 테스트 - bookId가 없을 때")
    void When_DeleteBookCategory_Expect_success() {
        when(bookCategoryRepository.existsByPk_BookId(anyLong())).thenReturn(false);
        assertThrows(NotFoundException.class,
            () -> bookCategoryService.deleteBookCategory(1L, new BookCategoryDeleteRequestDto()));
    }
}