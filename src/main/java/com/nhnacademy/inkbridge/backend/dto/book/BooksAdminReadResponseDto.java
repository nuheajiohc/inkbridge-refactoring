package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * class: BooksAdminReadResponse.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class BooksAdminReadResponseDto {

    private final Page<BooksAdminPaginationReadResponseDto> booksAdminPaginationReadResponseDtos;
    private final List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDtos;

    @Builder
    public BooksAdminReadResponseDto(
        Page<BooksAdminPaginationReadResponseDto> booksAdminPaginationReadResponseDtos,
        List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDtos) {
        this.booksAdminPaginationReadResponseDtos = booksAdminPaginationReadResponseDtos;
        this.authorPaginationReadResponseDtos = authorPaginationReadResponseDtos;
    }
}
