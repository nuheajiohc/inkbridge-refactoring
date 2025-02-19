package com.nhnacademy.inkbridge.backend.dto.review;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * class: ReviewCreateRequestDto.
 *
 * @author minm063
 * @version 2024/03/19
 */
@Getter
public class ReviewUpdateRequestDto {

    @Size(min = 3, max = 50)
    @NotBlank
    private final String reviewTitle;
    @Size(max = 1000)
    private final String reviewContent;
    @Min(value = 1)
    @Max(value = 5)
    private final Integer score;

    @Builder
    public ReviewUpdateRequestDto(String reviewTitle, String reviewContent, Integer score) {
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.score = score;
    }
}
