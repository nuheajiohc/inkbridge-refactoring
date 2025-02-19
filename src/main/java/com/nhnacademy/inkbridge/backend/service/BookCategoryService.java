package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryDeleteRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import java.util.List;

/**
 * class: BookCategoryService.
 *
 * @author choijaehun
 * @version 2/17/24
 */
public interface BookCategoryService {

    void createBookCategory(BookCategoryCreateRequestDto request);
    List<BookCategoryReadResponseDto> readBookCategory(Long bookId);

    void deleteBookCategory(Long bookId, BookCategoryDeleteRequestDto request);
}
