package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import java.util.List;

/**
 * class: AccumulationRatePolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
public interface AccumulationRatePolicyRepositoryCustom {
    /**
     * 적립율 정책 전체 내역을 조회하는 메소드입니다.
     *
     * @return List - AccumulationRatePolicyAdminReadResponseDto
     */
    List<AccumulationRatePolicyAdminReadResponseDto> findAllAccumulationRatePolicies();

    /**
     * 적립율 정책 id로 정책 내역을 조회하는 메소드입니다.
     *
     * @param accumulationRatePolicyId Long
     * @return AccumulationRatePolicyReadResponseDto
     */
    AccumulationRatePolicyReadResponseDto findAccumulationRatePolicy(Long accumulationRatePolicyId);

    /**
     * 현재 적용되는 적립율 정책을 조회하는 메소드입니다.
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    AccumulationRatePolicyReadResponseDto findCurrentAccumulationRatePolicy();

    /**
     * 적용중인 적립률을 조회하는 메소드입니다.
     *
     * @return 적립률
     */
    Integer findByCurrentAccumulationRate();
}
