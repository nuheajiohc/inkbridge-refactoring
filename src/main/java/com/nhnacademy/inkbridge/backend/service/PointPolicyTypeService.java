package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeUpdateRequestDto;
import java.util.List;

/**
 * class: PointPolicyTypeService.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
public interface PointPolicyTypeService {

    /**
     * 포인트 정책 유형 조회 메소드 입니다.
     *
     * @return List - PointPolicyTypeReadResponseDto
     */
    List<PointPolicyTypeReadResponseDto> getPointPolicyTypes();

    /**
     * 포인트 정책 유형 생성 메소드 입니다.
     *
     * @param pointPolicyTypeCreateRequestDto PointPolicyTypeCreateRequestDto
     */
    void createPointPolicyType(
        PointPolicyTypeCreateRequestDto pointPolicyTypeCreateRequestDto);

    /**
     * 포인트 정책 유형 수정 메소드 입니다.
     *
     * @param pointPolicyTypeUpdateRequestDto PointPolicyTypeUpdateRequestDto
     */
    void updatePointPolicyType(
        PointPolicyTypeUpdateRequestDto pointPolicyTypeUpdateRequestDto);

    /**
     * 포인트 정책 유형 삭제 메소드 입니다.
     *
     * @param pointPolicyTypeId Integer
     */
    void deletePointPolicyTypeById(Integer pointPolicyTypeId);
}
