package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.enums.PublisherMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * class: PublisherServiceImpl.
 *
 * @author choijaehun
 * @version 2024/03/20
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPublisher(PublisherCreateRequestDto request) {
        Publisher publisher = PublisherCreateRequestDto.toPublisher(request);
        publisherRepository.save(publisher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PublisherReadResponseDto> readPublishers(Pageable pageable) {
        return publisherRepository.findAllBy(pageable)
            .map(PublisherReadResponseDto::toPublisherReadResponseDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePublisher(Long publisherId,
        PublisherUpdateRequestDto request) {
        Publisher publisher = publisherRepository.findById(publisherId)
            .orElseThrow(() -> new NotFoundException(
                PublisherMessageEnum.PUBLISHER_NOT_FOUND.getMessage()));
        publisher.updatePublisher(request.getPublisherName());
    }
}
