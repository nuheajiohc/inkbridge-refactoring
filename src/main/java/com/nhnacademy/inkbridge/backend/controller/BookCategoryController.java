package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryDeleteRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: BookCategoryController.
 *
 * @author choijaehun
 * @version 2/17/24
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book-category")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    @PostMapping
    public ResponseEntity<HttpStatus> createBookCategory(
        @RequestBody BookCategoryCreateRequestDto request) {
        bookCategoryService.createBookCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{bookId}")
    public ResponseEntity<List<BookCategoryReadResponseDto>> readBookCategoryByBookId(
        @PathVariable Long bookId) {
        List<BookCategoryReadResponseDto> bookCategoryReadResponseDto = bookCategoryService.readBookCategory(
            bookId);
        return new ResponseEntity<>(bookCategoryReadResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<HttpStatus> deleteBookCategory(@PathVariable Long bookId, @RequestBody
        BookCategoryDeleteRequestDto request){
        bookCategoryService.deleteBookCategory(bookId,request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
