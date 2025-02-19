package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorCreateUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: AuthorService.
 *
 * @author minm063
 * @version 2024/03/14
 */
public interface AuthorService {

    /**
     * 도서 정보를 포함한 작가 상세 정보를 조회하는 메서드입니다.
     *
     * @param authorId Long
     * @return AuthorReadResponseDto
     */
    AuthorInfoReadResponseDto getAuthor(Long authorId);

    /**
     * 작가 이름으로 작가를 조회하는 메서드입니다.
     *
     * @param authorName String
     * @return AuthorInfoReadResponseDto
     */
    List<AuthorInfoReadResponseDto> getAuthorsByName(String authorName);

    /**
     * 작가 전체 목록을 조회하는 메서드입니다.
     *
     * @param pageable Pageable
     * @return AuthorInfoReadResponseDto
     */
    Page<AuthorInfoReadResponseDto> getAuthors(Pageable pageable);

    /**
     * 작가 정보를 등록하는 메서드입니다.
     *
     * @param authorFile                   File Entity
     * @param authorCreateUpdateRequestDto AuthorCreateUpdateRequestDto
     */
    void createAuthor(File authorFile,
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto);

    /**
     * 작가 정보를 수정하는 메서드입니다.
     *
     * @param authorFile                   File Entity
     * @param authorCreateUpdateRequestDto AuthorCreateUpdateRequestDto
     * @param authorId                     Long
     */
    void updateAuthor(File authorFile,
        AuthorCreateUpdateRequestDto authorCreateUpdateRequestDto,
        Long authorId);
}
