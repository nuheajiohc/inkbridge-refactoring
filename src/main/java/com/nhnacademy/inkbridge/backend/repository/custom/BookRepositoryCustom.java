package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookOrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.wish.BookWishReadResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: BookRepositoryCustom.
 *
 * @author minm063
 * @version 2024/02/15
 */
@NoRepositoryBean
public interface BookRepositoryCustom {

    /**
     * 메인 페이지 도서 목록 조회 메서드입니다.
     *
     * @param pageable pagination
     * @return 메인 페이지 도서 목록 조회 데이터
     */
    Page<BooksPaginationReadResponseDto> findAllBooks(Pageable pageable);

    /**
     * parameter(bookId)에 대한 상세 도서 조회 메서드입니다.
     *
     * @param bookId   Long
     * @param memberId Long
     * @return 도서 상세 조회 데이터
     */
    Optional<BookDetailReadResponseDto> findByBookId(Long bookId, Long memberId);

    /**
     * admin 도서 목록 페이지 조회 메서드입니다.
     *
     * @param pageable pagination
     * @return admin 도서 목록 조회 데이터
     */
    Page<BooksAdminPaginationReadResponseDto> findAllBooksByAdmin(Pageable pageable);

    /**
     * admin 도서 상세 페이지 조회 메서드입니다.
     *
     * @param bookId Long
     * @return admin 도서 상세 조회 데이터
     */
    Optional<BookAdminSelectedReadResponseDto> findBookByAdminByBookId(Long bookId);

    /**
     * 카트 도서 조회 메서드입니다.
     *
     * @param bookIdList book id list
     * @return CartReadResponseDto
     */
    List<BookOrderReadResponseDto> findByBookIdIn(Set<Long> bookIdList);

    List<BookWishReadResponseDto> findByWishMemberId(Long memberId);
}
