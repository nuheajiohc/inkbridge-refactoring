package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import java.util.List;

/**
 * class: PointPolicyService.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyService {

    /**
     * 포인트 정책 전체 조회 메서드 입니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    List<PointPolicyAdminReadResponseDto> getPointPolicies();

    /**
     * 포인트 정책 생성 메서드 입니다.
     *
     * @param pointPolicyCreateRequestDto PointPolicyCreateRequestDto
     */
    void createPointPolicy(PointPolicyCreateRequestDto pointPolicyCreateRequestDto);

    /**
     * 포인트 정책 유형 Id로 포인트 정책 내역 리스트를 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return List - PointPolicyReadResponseDto
     */
    List<PointPolicyAdminReadResponseDto> getPointPoliciesByTypeId(Integer pointPolicyTypeId);

    /**
     * 현재 적용중인 포인트 정책 목록을 조회하는 메소드입니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    List<PointPolicyAdminReadResponseDto> getCurrentPointPolicies();

    /**
     * 포인트 정책 유형의 현재 적용중인 정책을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    PointPolicyReadResponseDto getCurrentPointPolicy(Integer pointPolicyTypeId);
}
