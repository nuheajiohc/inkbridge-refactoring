package com.nhnacademy.inkbridge.backend.dto.review;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 *  class: ReviewReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/20
 */
@Getter
public class ReviewBookReadResponseDto {

    private final Page<ReviewDetailOnBookReadResponseDto> reviewDetailReadResponseDtos;
    private final Map<Long, List<String>> reviewFiles;

    @Builder
    public ReviewBookReadResponseDto(
        Page<ReviewDetailOnBookReadResponseDto> reviewDetailReadResponseDtos,
        Map<Long, List<String>> reviewFiles) {
        this.reviewDetailReadResponseDtos = reviewDetailReadResponseDtos;
        this.reviewFiles = reviewFiles;
    }
}
