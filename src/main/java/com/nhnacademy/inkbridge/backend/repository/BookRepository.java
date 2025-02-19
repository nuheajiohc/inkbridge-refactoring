package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.book.BookStockResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.repository.custom.BookRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    /**
     * 도서 번호로 도서 재고를 조회하는 메서드입니다.
     *
     * @param bookId Long
     * @return BookStockResponseDto
     */
    List<BookStockResponseDto> findByBookIdIn(List<Long> bookId); // 뭔 재고가 실패했는지 알려줘야되나?

    List<Book> findBookByBookIdIn(List<Long> bookId);
}
