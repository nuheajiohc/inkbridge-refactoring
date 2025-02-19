package com.nhnacademy.inkbridge.backend.dto.book;

import lombok.Builder;
import lombok.Getter;

/**
 * class: PublisherReadResponseDto.
 *
 * @author minm063
 * @version 2024/02/29
 */
@Getter
public class PublisherReadResponseDto {

    private final Long publisherId;
    private final String publisherName;

    @Builder
    public PublisherReadResponseDto(Long publisherId, String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }
}
