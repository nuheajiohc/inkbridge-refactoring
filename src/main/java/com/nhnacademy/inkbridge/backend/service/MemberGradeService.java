package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeUpdateRequestDto;
import java.util.List;

/**
 * class: MemberGradeService.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */
public interface MemberGradeService {

    List<MemberGradeReadResponseDto> getGradeList();

    void updateGrade(Integer gradeId, MemberGradeUpdateRequestDto memberGradeUpdateRequestDto);
}
