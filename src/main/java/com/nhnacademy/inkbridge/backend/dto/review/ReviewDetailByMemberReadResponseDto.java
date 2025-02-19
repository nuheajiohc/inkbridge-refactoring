package com.nhnacademy.inkbridge.backend.dto.review;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Builder;
import lombok.Getter;

/**
 *  class: ReviewReadResponseDto.
 *
 *  @author minm063
 *  @version 2024/03/19
 */
@Getter
public class ReviewDetailByMemberReadResponseDto {

    private final Long reviewId;
    private final String reviewTitle;
    private final String reviewContent;
    private final String registeredAt;
    private final Integer score;
    private final Long bookId;
    private final String bookTitle;
    private final String thumbnail;

    @Builder
    public ReviewDetailByMemberReadResponseDto(Long reviewId, String reviewTitle,
        String reviewContent, LocalDateTime registeredAt, Integer score, Long bookId,
        String bookTitle, String thumbnail) {

        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.registeredAt = registeredAt.atZone(ZoneId.of("Asia/Seoul"))
            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.score = score;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.thumbnail = thumbnail;
    }
}
