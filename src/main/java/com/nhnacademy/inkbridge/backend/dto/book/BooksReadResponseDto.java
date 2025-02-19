package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * class: BooksReadResponseDto.
 *
 * @author minm063
 * @version 2024/03/13
 */
@Getter
public class BooksReadResponseDto {

    private final Page<BooksPaginationReadResponseDto> booksPaginationReadResponseDtos;
    private final List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDto;

    @Builder
    public BooksReadResponseDto(
        Page<BooksPaginationReadResponseDto> booksPaginationReadResponseDtos,
        List<AuthorPaginationReadResponseDto> authorPaginationReadResponseDto) {
        this.booksPaginationReadResponseDtos = booksPaginationReadResponseDtos;
        this.authorPaginationReadResponseDto = authorPaginationReadResponseDto;
    }
}
