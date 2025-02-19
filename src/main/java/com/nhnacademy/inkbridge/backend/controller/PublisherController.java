package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.PublisherMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PublisherController.
 *
 * @author choijaehun
 * @version 2024/03/20
 */

@RestController
@RequestMapping("/api/admin/publishers")
@RequiredArgsConstructor
@Slf4j
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * 출판사등록 메소드
     *
     * @param request       출판사 이름이 들어있는 Dto
     * @param bindingResult 에러 확인
     * @return CREATED 상태코드
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createPublisher(
        @Valid @RequestBody PublisherCreateRequestDto request,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(PublisherMessageEnum.PUBLISHER_VALID_FAIL.getMessage());
        }

        publisherService.createPublisher(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 출판사 조회 메소드
     *
     * @param pageable 페이징 처리
     * @return 페이징 처리 된 출판사리스트
     */
    @GetMapping
    public ResponseEntity<Page<PublisherReadResponseDto>> readPublishers(Pageable pageable) {
        Page<PublisherReadResponseDto> response = publisherService.readPublishers(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 출판사 수정 메소드
     *
     * @param publisherId               수정할 출판사 아이디
     * @param publisherUpdateRequestDto 수정할 출판사 이름
     * @param bindingResult             에러 처리
     * @return OK 상태코드
     */
    @PutMapping("/{publisherId}")
    public ResponseEntity<HttpStatus> updatePublisher(@PathVariable Long publisherId,
        @Valid @RequestBody PublisherUpdateRequestDto publisherUpdateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(PublisherMessageEnum.PUBLISHER_VALID_FAIL.getMessage());
        }

        publisherService.updatePublisher(publisherId, publisherUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
