package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: PublisherService.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
public interface PublisherService {

    /**
     * 출판사 생성 메소드
     * @param request 출판사 생성 Dto
     */
    void createPublisher(PublisherCreateRequestDto request);

    /**
     * 출판사 조회 메소드
     * @param pageable 페이징 처리
     * @return 페이징 처리된 출판사 리스트
     */
    Page<PublisherReadResponseDto> readPublishers(Pageable pageable);

    /**
     * 출판사 수정 메소드
     * @param publisherId 출판사 아이디
     * @param publisherUpdateRequestDto 수정할 이름이 들어있는 출판사 Dto
     */
    void updatePublisher(Long publisherId, PublisherUpdateRequestDto publisherUpdateRequestDto);
}
