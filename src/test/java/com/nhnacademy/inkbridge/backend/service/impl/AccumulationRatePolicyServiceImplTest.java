package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.AccumulationRatePolicyRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: AccumulationRatePolicyServiceImplTest.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@ExtendWith(MockitoExtension.class)
class AccumulationRatePolicyServiceImplTest {

    @InjectMocks
    AccumulationRatePolicyServiceImpl accumulationRatePolicyService;

    @Mock
    AccumulationRatePolicyRepository accumulationRatePolicyRepository;

    @Test
    @DisplayName("적립율 정책 전체 내역 조회")
    void testGetAccumulationRatePolicies() {
        AccumulationRatePolicyAdminReadResponseDto responseDto = new AccumulationRatePolicyAdminReadResponseDto(
            1L, 5, LocalDate.of(2024, 1, 1));

        given(accumulationRatePolicyRepository.findAllAccumulationRatePolicies()).willReturn(
            List.of(responseDto));

        List<AccumulationRatePolicyAdminReadResponseDto> result = accumulationRatePolicyService.getAccumulationRatePolicies();

        assertAll(
            () -> assertEquals(1, result.size()),
            () -> assertEquals(responseDto, result.get(0))
        );
    }

    @Test
    @DisplayName("적립율 정책 id 조회 - 존재하지 않는 경우")
    void testGetAccumulationRatePolicy_not_found() {
        given(accumulationRatePolicyRepository.existsById(1L)).willReturn(false);

        assertThrows(NotFoundException.class, () -> accumulationRatePolicyService.getAccumulationRatePolicy(1L));

        verify(accumulationRatePolicyRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("적립율 정책 id 조회 - 조회 성공")
    void testGetAccumulationRatePolicy_success() {
        AccumulationRatePolicyReadResponseDto responseDto = new AccumulationRatePolicyReadResponseDto(1L, 5);

        given(accumulationRatePolicyRepository.existsById(1L)).willReturn(true);
        given(accumulationRatePolicyRepository.findAccumulationRatePolicy(1L)).willReturn(responseDto);

        AccumulationRatePolicyReadResponseDto result = accumulationRatePolicyService.getAccumulationRatePolicy(1L);

        assertEquals(responseDto, result);

        verify(accumulationRatePolicyRepository, times(1)).existsById(1L);
        verify(accumulationRatePolicyRepository, times(1)).findAccumulationRatePolicy(1L);
    }

    @Test
    @DisplayName("현재 적용되는 적립율 정책 조회")
    void testGetCurrentAccumulationRatePolicy() {
        AccumulationRatePolicyReadResponseDto responseDto = new AccumulationRatePolicyReadResponseDto(
            1L, 5);

        given(accumulationRatePolicyRepository.findCurrentAccumulationRatePolicy()).willReturn(responseDto);

        AccumulationRatePolicyReadResponseDto result = accumulationRatePolicyService.getCurrentAccumulationRatePolicy();

        assertEquals(responseDto, result);

        verify(accumulationRatePolicyRepository, times(1)).findCurrentAccumulationRatePolicy();
    }

    @Test
    @DisplayName("적립율 정책 생성")
    void testCreateAccumulationRatePolicy() {
        AccumulationRatePolicyCreateRequestDto requestDto = new AccumulationRatePolicyCreateRequestDto();
        requestDto.setAccumulationRate(5);

        accumulationRatePolicyService.createAccumulationRatePolicy(requestDto);

        verify(accumulationRatePolicyRepository, times(1)).save(any());
    }

}