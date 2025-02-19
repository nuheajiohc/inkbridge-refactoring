package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.entity.PointHistory;
import com.nhnacademy.inkbridge.backend.entity.PointPolicy;
import com.nhnacademy.inkbridge.backend.entity.PointPolicyType;
import com.nhnacademy.inkbridge.backend.enums.MemberMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import com.nhnacademy.inkbridge.backend.repository.PointHistoryRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyRepository;
import com.nhnacademy.inkbridge.backend.repository.PointPolicyTypeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: PointHistoryServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 3/24/24
 */

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceImplTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PointHistoryRepository pointHistoryRepository;
    @Mock
    private PointPolicyRepository pointPolicyRepository;
    @Mock
    private PointPolicyTypeRepository pointPolicyTypeRepository;

    @InjectMocks
    private PointHistoryServiceImpl pointHistoryService;

    private static List<PointHistoryReadResponseDto> testList;
    private static String reason;
    private static Long point;
    private static LocalDateTime accruedAt;
    private static Member member;

    @BeforeAll
    static void setUp() {
        testList = new ArrayList<>();
        reason = "test";
        point = 100L;
        accruedAt = LocalDateTime.now();
        testList.add(new PointHistoryReadResponseDto(reason, point, accruedAt));
        member = Member.create().build();
    }

    @Test
    void accumulatePointAtSignup() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(PointPolicy.class)));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mock(Member.class)));
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(
            mock(PointHistory.class));

        pointHistoryService.accumulatePointAtSignup(1L);

        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(pointHistoryRepository, times(1)).save(any());
    }

    @Test
    void givenInvalidPointPolicyType_whenAccumulatePointAtSignup_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtSignup(1L));
        assertEquals(notFoundException.getMessage(),
            PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
    }

    @Test
    void givenInvalidPointPolicy_whenAccumulatePointAtSignup_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtSignup(1L));
        assertEquals(notFoundException.getMessage(),
            PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidMember_whenAccumulatePointAtSignup_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(PointPolicy.class)));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtSignup(1L));
        assertEquals(notFoundException.getMessage(),
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenReview_accumulatePointAtReview() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(PointPolicy.class)));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mock(Member.class)));
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(
            mock(PointHistory.class));

        pointHistoryService.accumulatePointAtReview(1L, true);

        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(pointHistoryRepository, times(1)).save(any());
    }

    @Test
    void givenPhotoReview_whenAccumulatePointAtReview() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(PointPolicy.class)));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(mock(Member.class)));
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(
            mock(PointHistory.class));

        pointHistoryService.accumulatePointAtReview(1L, false);

        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).findById(anyLong());
        verify(pointHistoryRepository, times(1)).save(any());
    }

    @Test
    void givenInvalidPointPolicyType_whenAccumulatePointAtReview_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtReview(1L, true));
        assertEquals(notFoundException.getMessage(),
            PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
    }

    @Test
    void givenInvalidPointPolicyTypeAndImageReview_whenAccumulatePointAtReview_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtReview(1L, false));
        assertEquals(notFoundException.getMessage(),
            PointPolicyMessageEnum.POINT_POLICY_TYPE_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
    }

    @Test
    void givenInvalidPointPolicy_whenAccumulatePointAtReview_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtReview(1L, true));
        assertEquals(notFoundException.getMessage(),
            PointPolicyMessageEnum.POINT_POLICY_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidMember_whenAccumulatePointAtReview_thenThrowNotFoundException() {
        when(pointPolicyTypeRepository.findById(anyInt())).thenReturn(
            Optional.of(mock(PointPolicyType.class)));
        when(pointPolicyRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(PointPolicy.class)));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
            () -> pointHistoryService.accumulatePointAtReview(1L, true));
        assertEquals(notFoundException.getMessage(),
            MemberMessageEnum.MEMBER_NOT_FOUND.getMessage());
        verify(pointPolicyTypeRepository, times(1)).findById(anyInt());
        verify(pointPolicyRepository, times(1)).findById(anyLong());
        verify(memberRepository, times(1)).findById(anyLong());
    }

    @Test
    void getPointHistory() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(pointHistoryRepository.findByMemberOrderByAccruedAtDesc(member)).thenReturn(testList);
        List<PointHistoryReadResponseDto> result = pointHistoryService.getPointHistory(1L);

        assertEquals(reason, result.get(0).getReason());
        assertEquals(point, result.get(0).getPoint());
        assertEquals(accruedAt, result.get(0).getAccruedAt());
        assertEquals(testList.size(), result.size());
    }

    @Test
    void getPointHistoryWhenMemberNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pointHistoryService.getPointHistory(1L));
    }
}