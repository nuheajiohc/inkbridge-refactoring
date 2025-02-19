package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import java.util.List;

/**
 * class: AccumulationRatePolicyService.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public interface AccumulationRatePolicyService {

    /**
     * 적립율 정책 전체 내역을 조회하는 메소드입니다.
     *
     * @return List - AccumulationRatePolicyReadResponseDto
     */
    List<AccumulationRatePolicyAdminReadResponseDto> getAccumulationRatePolicies();

    /**
     * 적립율 정책 id로 내역을 조회하는 메소드입니다.
     *
     * @param accumulationRatePolicyId Long
     * @return AccumulationRatePolicyReadResponseDto
     */
    AccumulationRatePolicyReadResponseDto getAccumulationRatePolicy(Long accumulationRatePolicyId);

    /**
     * 현재 적용되는 적립율 정책을 조회하는 메소드입니다.
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    AccumulationRatePolicyReadResponseDto getCurrentAccumulationRatePolicy();

    /**
     * 적립율 정책을 생성하는 메소드입니다.
     *
     * @param requestDto AccumulationRatePolicyCreateRequestDto
     */
    void createAccumulationRatePolicy(AccumulationRatePolicyCreateRequestDto requestDto);

    /**
     * 적용중인 적립률을 조회하는 메소드입니다.
     *
     * @return 적립률
     */
    Integer getCurrentAccumulationRate();
}
