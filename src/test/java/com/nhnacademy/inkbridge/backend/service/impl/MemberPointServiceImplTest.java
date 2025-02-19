package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.entity.Member;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * class: MemberPointServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 3/11/24
 */

@ExtendWith(MockitoExtension.class)
class MemberPointServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberPointServiceImpl memberPointService;

    private static Member member;


    @BeforeAll
    static void setup() {
        Long point = 100L;
        member = Member.create().memberPoint(point).build();
    }

    @Test
    void getMemberPoint() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        assertEquals(member.getMemberPoint(), memberPointService.getMemberPoint(1L));
    }

    @Test
    void memberPointUpdate() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        memberPointService.memberPointUpdate(1L, 50L);

        assertEquals(150L, member.getMemberPoint());
    }

    @Test
    void memberPointUpdateWhenMemberNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> memberPointService.memberPointUpdate(1L, 50L));
    }

    @Test
    void memberPointUpdateWhenPointValidFail() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        assertThrows(ValidationException.class,
            () -> memberPointService.memberPointUpdate(1L, -200L));
    }


    @Test
    void getMemberPointWhenMemberNotFound() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> memberPointService.getMemberPoint(1L));
    }


}