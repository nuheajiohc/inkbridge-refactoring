package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.PointHistory;
import com.nhnacademy.inkbridge.backend.entity.enums.PointHistoryReason;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.MemberPointMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import com.nhnacademy.inkbridge.backend.service.MemberPointService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberPointServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */
@Service
@RequiredArgsConstructor
public class MemberPointServiceImpl implements MemberPointService {

    private final MemberRepository memberRepository;
    private final PointHistoryRepository pointHistoryRepository;

    /**
     * 지정된 회원의 포인트를 업데이트합니다.
     *
     * @param memberId   회원의 ID
     * @param pointValue 변경할 포인트 값
     */
    @Override
    public void memberPointUpdate(Long memberId, Long pointValue) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        member.updateMemberPoint(pointValue);
        if (member.getMemberPoint() < 0) {
            throw new ValidationException(
                MemberPointMessageEnum.MEMBER_POINT_VALID_FAIL.getMessage());
        }
    }

    /**
     * 지정된 회원의 포인트를 업데이트합니다. 포인트가 음수가 되는 경우 {@link ValidationException}을 발생시킵니다.
     *
     * @param memberId   회원의 ID
     * @param pointValue 변경할 포인트 값
     * @throws NotFoundException   회원을 찾을 수 없는 경우 발생
     * @throws ValidationException 포인트가 음수가 될 경우 발생
     */
    @Override
    @Transactional
    public void memberPointUpdate(Long memberId, Long pointValue,
        PointHistoryReason pointHistoryReason) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        pointHistoryRepository.save(
            PointHistory.builder().point(pointValue).member(member).accruedAt(LocalDateTime.now())
                .reason(pointHistoryReason.getMessage()).build());
        member.updateMemberPoint(pointValue);
        if (member.getMemberPoint() < 0) {
            throw new ValidationException(
                MemberPointMessageEnum.MEMBER_POINT_VALID_FAIL.getMessage());
        }
    }

    /**
     * 지정된 회원의 현재 포인트를 조회합니다.
     *
     * @param memberId 회원의 ID
     * @return 조회된 회원의 포인트
     * @throws NotFoundException 회원을 찾을 수 없는 경우 발생
     */
    @Override
    @Transactional(readOnly = true)
    public Long getMemberPoint(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(
            MemberMessageEnum.MEMBER_NOT_FOUND.name()));
        return member.getMemberPoint();
    }
}
