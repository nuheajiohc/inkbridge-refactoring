package com.nhnacademy.inkbridge.backend.dto.review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 *  class: ReviewOnBookReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/21
 */
@Getter
public class ReviewDetailOnBookReadResponseDto {

    private final Long reviewId;
    private final String reviewerEmail;
    private final String reviewTitle;
    private final String reviewContent;
    private final LocalDate registeredAt;
    private final Integer score;

    @Builder
    public ReviewDetailOnBookReadResponseDto(Long reviewId, String reviewerEmail,
        String reviewTitle, String reviewContent, LocalDateTime registeredAt, Integer score) {
        this.reviewId = reviewId;
        this.reviewerEmail = reviewerEmail;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.registeredAt = registeredAt.toLocalDate();
        this.score = score;
    }
}
