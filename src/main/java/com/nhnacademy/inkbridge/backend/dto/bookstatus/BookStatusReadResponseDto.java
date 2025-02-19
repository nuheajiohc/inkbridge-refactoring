package com.nhnacademy.inkbridge.backend.dto.bookstatus;

import lombok.Builder;
import lombok.Getter;

/**
 * class: BookStatusReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/27
 */
@Getter
public class BookStatusReadResponseDto {

    private final Long statusId;
    private final String statusName;

    @Builder
    public BookStatusReadResponseDto(Long statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }
}
