package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookAdminReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/29
 */
@Getter
public class BookAdminReadResponseDto {

    List<ParentCategoryReadResponseDto> parentCategoryReadResponseDtoList;
    List<PublisherReadResponseDto> publisherReadResponseDtoList;
    List<AuthorReadResponseDto> authorReadResponseDtoList;
    List<BookStatusReadResponseDto> bookStatusReadResponseDtoList;
    List<TagReadResponseDto> tagReadResponseDtoList;

    @Builder
    public BookAdminReadResponseDto(
        List<ParentCategoryReadResponseDto> parentCategoryReadResponseDtoList,
        List<PublisherReadResponseDto> publisherReadResponseDtoList,
        List<AuthorReadResponseDto> authorReadResponseDtoList,
        List<BookStatusReadResponseDto> bookStatusReadResponseDtoList,
        List<TagReadResponseDto> tagReadResponseDtoList) {
        this.parentCategoryReadResponseDtoList = parentCategoryReadResponseDtoList;
        this.publisherReadResponseDtoList = publisherReadResponseDtoList;
        this.authorReadResponseDtoList = authorReadResponseDtoList;
        this.bookStatusReadResponseDtoList = bookStatusReadResponseDtoList;
        this.tagReadResponseDtoList = tagReadResponseDtoList;
    }
}
