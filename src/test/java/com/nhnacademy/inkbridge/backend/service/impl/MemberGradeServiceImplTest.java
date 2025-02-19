package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import java.math.BigDecimal;
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
 * class: MemberGradeServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */

@ExtendWith(MockitoExtension.class)
class MemberGradeServiceImplTest {

    @Mock
    private MemberGradeRepository memberGradeRepository;

    @InjectMocks
    private MemberGradeServiceImpl memberGradeService;

    private static MemberGrade memberGrade;
    private static Integer gradeId;
    private static String grade;
    private static BigDecimal pointRate;
    private static Long standardAmount;

    @BeforeAll
    static void setTest() {
        gradeId = 1;
        grade = "TEST";
        pointRate = BigDecimal.valueOf(1.5);
        standardAmount = 10000L;
        memberGrade = MemberGrade.create().grade(grade).gradeId(gradeId)
            .standardAmount(standardAmount)
            .pointRate(pointRate).build();
    }

    @Test
    void getGradeList() {
        List<MemberGrade> memberGrades = new ArrayList<>();
        memberGrades.add(memberGrade);
        when(memberGradeRepository.findAll()).thenReturn(memberGrades);
        assertEquals(1, memberGradeService.getGradeList().size());
    }

    @Test
    void updateGradeSuccessfully() {
        MemberGradeUpdateRequestDto memberGradeUpdateRequestDto = new MemberGradeUpdateRequestDto();
        memberGradeUpdateRequestDto.setPointRate(BigDecimal.valueOf(2.0));
        memberGradeUpdateRequestDto.setStandardAmount(10L);

        when(memberGradeRepository.findById(gradeId)).thenReturn(Optional.of(memberGrade));

        memberGradeService.updateGrade(gradeId, memberGradeUpdateRequestDto);

        // then
        assertEquals(BigDecimal.valueOf(2.0), memberGrade.getPointRate());
        assertEquals(Long.valueOf(10L), memberGrade.getStandardAmount());

        verify(memberGradeRepository, times(1)).save(memberGrade);
    }
    @Test
    void getGradeListWhenTagNotFound() {
        MemberGradeUpdateRequestDto memberGradeUpdateRequestDto = new MemberGradeUpdateRequestDto();

        when(memberGradeRepository.findById(gradeId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
            () -> memberGradeService.updateGrade(gradeId, memberGradeUpdateRequestDto));
    }

}