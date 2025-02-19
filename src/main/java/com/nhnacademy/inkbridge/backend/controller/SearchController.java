package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.search.BookSearchResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Search;
import com.nhnacademy.inkbridge.backend.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: SearchController.
 *
 * @author choijaehun
 * @version 2024/03/11
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final BookSearchService bookSearchService;

    /**
     * 키워드로 검색 하는 메소드
     * @param text 키워드
     * @param pageable 페이징 처리
     * @return 페이징 처리된 bookDto
     */
    @GetMapping("/search")
    public ResponseEntity<Page<BookSearchResponseDto>> searchByText(@RequestParam String text,
        Pageable pageable) {
        Page<Search> searchPage = bookSearchService.searchByText(text,
            pageable);
        Page<BookSearchResponseDto> books = searchPage.map(
            BookSearchResponseDto::toBookSearchResponseDto);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * 필드명에 해당하는 도서들 조회하는 메소드
     * @param pageable 페이징 처리
     * @return 페이징 처리된 bookDto
     */
    @GetMapping("/books/filter")
    public ResponseEntity<Page<BookSearchResponseDto>> searchByAll(Pageable pageable) {
        Page<Search> searchPage = bookSearchService.searchByAll(pageable);
        Page<BookSearchResponseDto> books = searchPage.map(
            BookSearchResponseDto::toBookSearchResponseDto);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * 카테고리명에 해당하는 도서들 조회하는 메소드
     * @param category 카테고리명
     * @param pageable 페이징 처리
     * @return 페이징 처리된 bookDto
     */
    @GetMapping("/categories/{category}/books")
    public ResponseEntity<Page<BookSearchResponseDto>> readByCategory(@PathVariable String category, Pageable pageable){
        Page<Search> searchPage = bookSearchService.searchByCategory(category,pageable);
        Page<BookSearchResponseDto> books = searchPage.map(BookSearchResponseDto::toBookSearchResponseDto);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
}