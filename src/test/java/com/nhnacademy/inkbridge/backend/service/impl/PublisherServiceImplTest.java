package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * class: PublisherServiceImplTest.
 *
 * @author choijaehun
 * @version 2024/03/20
 */

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

    @InjectMocks
    PublisherServiceImpl publisherService;

    @Mock
    PublisherRepository publisherRepository;

    @Test
    @DisplayName("출판사 생성 테스트 - 성공")
    void when_createPublisher_expect_success() {
        PublisherCreateRequestDto request = new PublisherCreateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "nhn 문고");

        when(publisherRepository.save(any())).thenReturn(new Publisher(request.getPublisherName()));
        publisherService.createPublisher(request);
        verify(publisherRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("출판사 조회 테스트 - 성공")
    void when_readPublisher_expect_success() {
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(new Publisher("Publisher 1"));
        publishers.add(new Publisher("Publisher 2"));
        Page<Publisher> page = new PageImpl<>(publishers);

        Pageable pageable = mock(Pageable.class);
        when(publisherRepository.findAllBy(pageable)).thenReturn(page);

        Page<PublisherReadResponseDto> result = publisherService.readPublishers(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("Publisher 1", result.getContent().get(0).getPublisherName());
        assertEquals("Publisher 2", result.getContent().get(1).getPublisherName());
        verify(publisherRepository, times(1)).findAllBy(any());
    }

    @Test
    @DisplayName("출판사 수정 테스트 - 성공")
    void when_updatePublisher_expect_success() {
        Long publisherId = 1L;
        PublisherUpdateRequestDto request = new PublisherUpdateRequestDto();
        ReflectionTestUtils.setField(request, "publisherName", "Updated Publisher Name");

        Publisher existingPublisher = new Publisher("Old Publisher Name");
        ReflectionTestUtils.setField(existingPublisher, "publisherId", publisherId);

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(existingPublisher));
        publisherService.updatePublisher(publisherId, request);

        verify(publisherRepository, times(1)).findById(publisherId);
        assertEquals(request.getPublisherName(), existingPublisher.getPublisherName());
    }

    @Test
    @DisplayName("출판사 수정 테스트 - 유효성 검사 실패")
    void when_updatePublisher_expect_fail() {
        Long publisherId = 1L;
        PublisherUpdateRequestDto requestDto = new PublisherUpdateRequestDto();
        assertThrows(NotFoundException.class,
            () -> publisherService.updatePublisher(publisherId, requestDto));
    }
}
