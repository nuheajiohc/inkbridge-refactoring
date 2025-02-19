package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import java.util.List;

/**
 * class: WrappingService.
 *
 * @author JBum
 * @version 2024/02/28
 */
public interface WrappingService {

    /**
     * 모든 포장지 가져오기.
     *
     * @param isActive 활성여부 ture / false
     * @returnr 모든 포장지
     */
    List<WrappingResponseDto> getWrappingList(boolean isActive);

    /**
     * wrappingId의 포장지정보 가져오기.
     *
     * @param wrappingId 조회할 포장지 Id
     * @return 조회한 wrapping
     */
    WrappingResponseDto getWrapping(Long wrappingId);

    /**
     * 새로운 포장지 만들기
     *
     * @param wrappingCreateRequestDto 새로운 포장지 정보
     */
    void createWrapping(WrappingCreateRequestDto wrappingCreateRequestDto);

    /**
     * 기존 포장지 새로운 정보로 업데이트
     *
     * @param wrappingId               기존 포장지 Id
     * @param wrappingCreateRequestDto 새로운 포장지 정보
     */
    void updateWrapping(Long wrappingId, WrappingCreateRequestDto wrappingCreateRequestDto);
}
