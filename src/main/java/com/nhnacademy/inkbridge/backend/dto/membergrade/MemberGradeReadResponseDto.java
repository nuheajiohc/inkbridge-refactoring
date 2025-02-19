package com.nhnacademy.inkbridge.backend.dto.membergrade;

import com.nhnacademy.inkbridge.backend.entity.MemberGrade;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * class: MemberGradeReadResponseDto.
 *
 * @author jeongbyeonghun
 * @version 3/6/24
 */

@Getter
public class MemberGradeReadResponseDto {

    private final Integer gradeId;
    private final String grade;
    private final BigDecimal pointRate;
    private final Long standardAmount;

    @Builder
    public MemberGradeReadResponseDto(MemberGrade memberGrade) {
        this.grade = memberGrade.getGrade();
        this.gradeId = memberGrade.getGradeId();
        this.pointRate = memberGrade.getPointRate();
        this.standardAmount = memberGrade.getStandardAmount();
    }
}
