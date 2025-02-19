package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookSearchRepositoryCustom.
 *
 * @author choijaehun
 * @version 2024/03/11
 */
public interface BookSearchRepositoryCustom {

    /**
     * 키워드명으로 도서 검색하는 메소드
     * @param text 키워드
     * @param pageable 페이징처리
     * @return 페이징객체
     */
    Page<Search> searchByText(String text, Pageable pageable);

    /**
     * 필드명에 해당하는 도서 찾는 메소드
     * @param pageable 페이징 처리
     * @return 페이징객체
     */
    Page<Search> searchByAll(Pageable pageable);

    /**
     * 카테고리 명으로 도서 찾는 메소드
     * @param category 카테고리명
     * @param pageable 페이징 처리
     * @return 페이징 객체
     */
    Page<Search> searchByCategory(String category,Pageable pageable);

}
