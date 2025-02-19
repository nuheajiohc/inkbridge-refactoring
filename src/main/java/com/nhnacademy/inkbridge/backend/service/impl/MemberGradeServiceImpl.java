package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import com.nhnacademy.inkbridge.backend.enums.MemberGradeMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.MemberGradeRepository;
import com.nhnacademy.inkbridge.backend.service.MemberGradeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: MemberGradeServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */

@Service
@RequiredArgsConstructor
public class MemberGradeServiceImpl implements MemberGradeService {

    private final MemberGradeRepository memberGradeRepository;

    @Override
    @Transactional
    public List<MemberGradeReadResponseDto> getGradeList() {
        List<MemberGrade> gradeList = memberGradeRepository.findAll();
        return gradeList.stream().map(MemberGradeReadResponseDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateGrade(Integer gradeId,
        MemberGradeUpdateRequestDto memberGradeUpdateRequestDto) {

        MemberGrade memberGrade = memberGradeRepository.findById(gradeId)
            .orElseThrow(() -> new NotFoundException(
                MemberGradeMessageEnum.MEMBER_GRADE_NOT_FOUND_ERROR.getMessage()));

        memberGrade.updateGrade(memberGradeUpdateRequestDto);
        memberGradeRepository.save(memberGrade);
    }
}
