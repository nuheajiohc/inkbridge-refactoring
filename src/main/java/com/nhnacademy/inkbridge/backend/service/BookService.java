package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookOrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookStockUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

/**
 * class: BookService.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookService {

    /**
     * page에 따른 전체 도서를 가져오는 메서드입니다.
     *
     * @param pageable pageable
     * @return BooksReadResponseDto
     */
    BooksReadResponseDto readBooks(Pageable pageable);

    /**
     * bookId에 따른 도서 목록을 가져오는 메서드입니다.
     *
     * @param bookIdList Set
     * @return CartReadResponseDto
     */
    List<BookOrderReadResponseDto> getCartBooks(Set<Long> bookIdList);

    /**
     * Book Id값으로 dto에 대한 데이터를 가져오는 메서드입니다. parameter가 데이터베이스에 저장되어 있지 않을 시 NotFoundException을
     * 던집니다.
     *
     * @return BookReadResponseDto
     */
    BookReadResponseDto readBook(Pageable pageable, Long bookId, Long memberId);

    /**
     * admin 페이지에서 필요한 전체 도서 관련 데이터를 가져오는 메서드입니다.
     *
     * @param pageable pageable
     * @return BooksAdminReadResponseDto
     */
    BooksAdminReadResponseDto readBooksByAdmin(Pageable pageable);

    /**
     * admin 페이지에서 저장된 상세 도서 관련 데이터를 가져오는 메서드입니다. parameter가 데이터베이스에 저장되어 있지 않을 시 NotFoundException을
     * 던진다.
     *
     * @param bookId 도서 id, 0보다 커야 한다
     * @return BookAdminReadResponseDto
     */
    BookAdminDetailReadResponseDto readBookByAdmin(Long bookId);

    /**
     * admin 페이지에서 필요한 도서와 관련된 리스트를 가져오는 메서드입니다.
     *
     * @return BookAdminReadResponseDto
     */
    BookAdminReadResponseDto readBookByAdmin();

    /**
     * 입력값에 대해 새로운 Book을 데이터베이스에 추가하는 메서드입니다. 해당하는 BookStatus, File, Publisher가 데이터베이스에 저장되어 있지 않을 시
     * NotFoundException을 던진다.
     *
     * @param thumbnail                 MultipartFile
     * @param bookAdminCreateRequestDto BookAdminCreateRequestDto
     */
    void createBook(File thumbnail, BookAdminCreateRequestDto bookAdminCreateRequestDto);

    /**
     * 입력값에 대해 도서 정보를 수정하는 메서드입니다. 해당하는 BookStatus, File, Publisher가 데이터베이스에 저장되어 있지 않을 시
     * NotFoundException을 던진다.
     *
     * @param bookId                    Long
     * @param bookAdminUpdateRequestDto BookAdminUpdateResponseDto
     */
    void updateBookByAdmin(Long bookId, File thumbnail,
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto);

    /**
     * 저장된 재고와 param 값을 비교하는 메서드입니다. 재고가 부족하면 ConflictException을 던집니다.
     *
     * @param bookStockUpdateRequestDtos BookStockUpdateRequestDto List
     * @return Boolean
     */
    Boolean validateStock(List<BookStockUpdateRequestDto> bookStockUpdateRequestDtos);

    /**
     * 재고를 수정하는 메서드입니다.
     */
    void updateStock(List<BookStockUpdateRequestDto> bookStockUpdateRequestDtos);

}
