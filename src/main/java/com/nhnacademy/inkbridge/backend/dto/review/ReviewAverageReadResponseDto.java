package com.nhnacademy.inkbridge.backend.dto.review;

import lombok.Builder;
import lombok.Getter;

/**
 *  class: ReviewAverageReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class ReviewAverageReadResponseDto {

    private final Double avg;
    private final Long count;

    @Builder
    public ReviewAverageReadResponseDto(Double avg, Long count) {
        this.avg = Double.parseDouble(String.format("%.1f", avg));
        this.count = count;
    }
}
