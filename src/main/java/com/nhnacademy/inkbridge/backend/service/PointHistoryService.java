package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import java.util.List;

/**
 * class: PointHistoryService.
 *
 * @author devminseo
 * @version 3/19/24
 */
public interface PointHistoryService {

    /**
     * 회원이 회원가입시 회원가입 축하금을 지급하는 메서드입니다.
     *
     * @param memberId 지급할 회원 아이디
     */
    void accumulatePointAtSignup(Long memberId);

    /**
     * 특정 사용자의 포인트 이력을 조회합니다.
     * 사용자의 ID를 매개변수로 받아 해당 사용자에 대한 모든 포인트 변동 이력을 조회하며,
     * 조회된 포인트 이력 정보를 {@link PointHistoryReadResponseDto} 객체의 리스트 형태로 반환합니다.
     *
     * @param userId 포인트 이력을 조회할 사용자의 ID
     * @return 조회된 포인트 이력 정보를 담은 {@link PointHistoryReadResponseDto} 객체의 리스트
     */
    List<PointHistoryReadResponseDto> getPointHistory(Long userId);

    /**
     * 회원이 리뷰 작성 시 포인트를 적립합니다.
     *
     * @param memberId member id
     * @return point policy id
     */
    Integer accumulatePointAtReview(Long memberId, boolean isPhotoReview);
}
