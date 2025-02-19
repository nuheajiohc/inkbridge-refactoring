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
public class ReviewMemberReadResponseDto {

    private final Page<ReviewDetailByMemberReadResponseDto> reviewDetailReadResponseDtos;
    private final Map<Long, List<String>> reviewFiles;
    private final Long count;

    @Builder
    public ReviewMemberReadResponseDto(
        Page<ReviewDetailByMemberReadResponseDto> reviewDetailReadResponseDtos,
        Map<Long, List<String>> reviewFiles, Long count) {
        this.reviewDetailReadResponseDtos = reviewDetailReadResponseDtos;
        this.reviewFiles = reviewFiles;
        this.count = count;
    }
}
