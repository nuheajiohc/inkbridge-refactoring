package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookSearchService.
 *
 * @author choijaehun
 * @version 2024/03/10
 */
public interface BookSearchService {

    /**
     * 키워드에 해당하는 책을 조회하는 메소드
     * @param text 키워드
     * @param pageable 페이징 처리
     * @return 페이지 객체
     */
    Page<Search> searchByText(String text, Pageable pageable);

    /**
     * 필드명에 해당하는 책을 조회하는 메소드
     * @param pageable 페이징 처리
     * @return 페이지 객체
     */
    Page<Search> searchByAll(Pageable pageable);

    /**
     * 카테고리명에 해당하는 책을 조회하는 메소드
     * @param category 카테고리명
     * @param pageable 페이징 처리
     * @return 페이지 객체
     */
    Page<Search> searchByCategory(String category,Pageable pageable);
}
