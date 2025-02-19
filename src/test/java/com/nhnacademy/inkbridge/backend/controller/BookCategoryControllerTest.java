package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryDeleteRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookCategoryService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: BookCategoryControllerTest.
 *
 * @author choijaehun
 * @version 2/19/24
 */

@WebMvcTest(BookCategoryController.class)
class BookCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookCategoryService bookCategoryService;

    @Test
    @DisplayName("book-category 생성 테스트 - 성공")
    void When_CreateBookCategory_Expect_Success() throws Exception {
        BookCategoryCreateRequestDto request = new BookCategoryCreateRequestDto();
        ReflectionTestUtils.setField(request, "categoryId", 1L);
        ReflectionTestUtils.setField(request, "bookId", 1L);
        mockMvc.perform(
                post("/api/book-category")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        verify(bookCategoryService, times(1)).createBookCategory(request);
    }

    @Test
    @DisplayName("book-category 조회 테스트 - 성공")
    void When_ReadBookCategory_Expect_Success() throws Exception {
        Long bookId = 1L;
        when(bookCategoryService.readBookCategory(bookId)).thenReturn(List.of(
            new BookCategoryReadResponseDto()));

        mockMvc.perform(get("/api/book-category/{bookId}", bookId)
                .content(objectMapper.writeValueAsString(bookCategoryService.readBookCategory(bookId))))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("book-category 삭제 테스트 - 성공")
    void When_DeleteBookCategory_Expect_Success() throws Exception {
        Long bookId = 1L;
        Long categoryId = 1L;
        BookCategoryDeleteRequestDto request = new BookCategoryDeleteRequestDto();
        ReflectionTestUtils.setField(request, "categoryId", categoryId);

        mockMvc.perform(delete("/api/book-category/{bookId}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        verify(bookCategoryService, times(1)).deleteBookCategory(bookId, request);
    }
}