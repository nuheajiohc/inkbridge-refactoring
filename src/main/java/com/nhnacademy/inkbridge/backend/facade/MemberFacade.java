package com.nhnacademy.inkbridge.backend.facade;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberFacade.
 *
 * @author devminseo
 * @version 3/20/24
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MemberFacade {
    private final MemberService memberService;
    private final PointHistoryService pointHistoryService;

    /**
     * 회원가입후 , 가입축하금을 지급합니다.
     *
     * @param memberCreateRequestDto 회원가입 정보
     */
    public void signupFacade(MemberCreateRequestDto memberCreateRequestDto) {
        Long memberId = memberService.createMember(memberCreateRequestDto);
        pointHistoryService.accumulatePointAtSignup(memberId);
    }
}
