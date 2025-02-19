package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.PointHistory;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: PointHistoryServiceImpl.
 *
 * @author devminseo
 * @version 3/19/24
 */
@Service("pointHistoryService")
@RequiredArgsConstructor
@Transactional
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointPolicyRepository pointPolicyRepository;
    private final PointPolicyTypeRepository pointPolicyTypeRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final MemberRepository memberRepository;
    private static final Integer REGISTER = 1;
    private static final Integer REVIEW = 2;
    private static final Integer PHOTO_REVIEW = 3;


    /**
     * {@inheritDoc}
     */
    @Override
    public void accumulatePointAtSignup(Long memberId) {

        PointPolicyType pointType =
            pointPolicyTypeRepository.findById(REGISTER).orElseThrow(() -> new NotFoundException(
                PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));

        saveAccumulation(memberId, pointType, PointHistoryReason.REGISTER_MSG.getMessage());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer accumulatePointAtReview(Long memberId, boolean isReview) {
        PointPolicyType pointType;
        if (isReview) {
            pointType =
                pointPolicyTypeRepository.findById(REVIEW)
                    .orElseThrow(() -> new NotFoundException(
                        PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));
        } else {
            pointType = pointPolicyTypeRepository.findById(PHOTO_REVIEW)
                .orElseThrow(() -> new NotFoundException(
                    PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage()));
        }

        saveAccumulation(memberId, pointType, PointHistoryReason.REVIEW_MSG.getMessage());
        return pointType.getPointPolicyTypeId();
    }

    /**
     * 포인트 내역 적립의 공통 부분을 처리하는 메서드입니다.
     *
     * @param memberId member id
     * @param pointType {@link PointPolicyType}
     * @param message 적립 문구
     */
    private void saveAccumulation(Long memberId, PointPolicyType pointType, String message) {
        PointPolicy pointPolicy =
            pointPolicyRepository.findById(Long.valueOf(pointType.getPointPolicyTypeId()))
                .orElseThrow(
                    () -> new NotFoundException(
                        PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage()));
        Member member =
            memberRepository.findById(memberId)
                .orElseThrow(
                    () -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.getMessage()));

        member.updateMemberPoint(pointPolicy.getAccumulatePoint());

        PointHistory pointHistory = PointHistory.builder()
            .reason(message)
            .point(pointPolicy.getAccumulatePoint())
            .accruedAt(LocalDateTime.now())
            .member(member)
            .build();

        pointHistoryRepository.save(pointHistory);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PointHistoryReadResponseDto> getPointHistory(Long userId) {
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        return pointHistoryRepository.findByMemberOrderByAccruedAtDesc(member);
    }
}
