package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: PointPolicyRepositoryCustom.
 *
 * @author jangjaehun
 * @version 2024/02/16
 */
@NoRepositoryBean
public interface PointPolicyRepositoryCustom {

    /**
     * 포인트 정책 조회 메소드 입니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    List<PointPolicyAdminReadResponseDto> findAllPointPolicyBy();

    /**
     * 포인트 정책 유형 내역을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    List<PointPolicyAdminReadResponseDto> findAllPointPolicyByTypeId(Integer pointPolicyTypeId);

    /**
     * 현재 적용중인 포인트 정책 목록을 조회하는 메소드입니다. <br/> 가장 마지막에 등록된 정책번호로 조회합니다. <br/> 정렬 순서는 정책 유형의 id 번호 순으로
     * 정렬됩니다.
     *
     * @return List - PointPolicyReadResponseDto
     */
    List<PointPolicyAdminReadResponseDto> findAllCurrentPointPolicies();

    /**
     * 포인트 정책 유형에 맞는 현재 적용중인 정책을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    PointPolicyReadResponseDto findCurrentPointPolicy(Integer pointPolicyTypeId);
}
