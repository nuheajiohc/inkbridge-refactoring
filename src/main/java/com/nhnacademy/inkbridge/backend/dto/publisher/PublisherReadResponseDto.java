package com.nhnacademy.inkbridge.backend.dto.publisher;

import com.nhnacademy.inkbridge.backend.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * class: PublisherReadResponseDto.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@Getter
@AllArgsConstructor
public class PublisherReadResponseDto {

    private Long publisherId;
    private String publisherName;


    /**
     *  출판사 엔티티를 출판사 조회 Dto로 변환해주는 메소드
     * @param publisher 출판사 엔티티
     * @return 출판사 조회 Dto
     */
    public static PublisherReadResponseDto toPublisherReadResponseDto(Publisher publisher) {
        return new PublisherReadResponseDto(publisher.getPublisherId(),
            publisher.getPublisherName());
    }
}
