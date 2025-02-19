package com.nhnacademy.inkbridge.backend.dto.search;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: BookSearchPageResponseDto.
 *
 * @author choijaehun
 * @version 2024/03/19
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchPageResponseDto {
    private List<BookSearchResponseDto> content;
    private Long totalElements;
}
