package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Wrapping;
import com.nhnacademy.inkbridge.backend.enums.OrderMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.WrappingMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.WrappingRepository;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: WrappingServiceImpl.
 *
 * @author JBum
 * @version 2024/02/28
 */
@Service
public class WrappingServiceImpl implements WrappingService {

    private final WrappingRepository wrappingRepository;

    /**
     * wrappingService 생성자.
     *
     * @param wrappingRepository WrappingService에 주입해줄 WrappingRepository
     */
    public WrappingServiceImpl(WrappingRepository wrappingRepository) {
        this.wrappingRepository = wrappingRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<WrappingResponseDto> getWrappingList(boolean isActive) {
        return wrappingRepository.findAllByIsActive(isActive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WrappingResponseDto getWrapping(Long wrappingId) {
        return wrappingRepository.findByWrappingId(wrappingId)
            .orElseThrow(() -> new NotFoundException(
                OrderMessageEnum.WRAPPING_NOT_FOUND.getMessage()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createWrapping(WrappingCreateRequestDto wrappingCreateRequestDto) {
        Wrapping newWrapping = Wrapping.builder()
            .wrappingName(wrappingCreateRequestDto.getWrappingName())
            .price(wrappingCreateRequestDto.getPrice())
            .isActive(wrappingCreateRequestDto.getIsActive()).build();
        wrappingRepository.save(newWrapping);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 변경할 포장지가 존재하지 않을때
     */
    @Override
    @Transactional
    public void updateWrapping(Long wrappingId, WrappingCreateRequestDto wrappingCreateRequestDto) {
        Wrapping wrapping = wrappingRepository.findById(wrappingId)
            .orElseThrow(() -> new NotFoundException(
                WrappingMessageEnum.WRAPPING_NOT_FOUND.getMessage()));
        wrapping.update(wrappingCreateRequestDto.getWrappingName(),
            wrappingCreateRequestDto.getPrice(), wrappingCreateRequestDto.getIsActive());
    }
}
