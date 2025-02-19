package com.nhnacademy.inkbridge.backend.entity;

import com.nhnacademy.inkbridge.backend.dto.membergrade.MemberGradeUpdateRequestDto;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * class: memberGrade.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "member_grade")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGrade {

    @Id
    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "grade")
    private String grade;

    @Column(name = "point_rate")
    private BigDecimal pointRate;

    @Column(name = "standard_amount")
    private Long standardAmount;

    @Builder(builderMethodName = "create")
    public MemberGrade(Integer gradeId, String grade, BigDecimal pointRate, Long standardAmount) {
        this.gradeId = gradeId;
        this.grade = grade;
        this.pointRate = pointRate;
        this.standardAmount = standardAmount;
    }

    public void updateGrade(MemberGradeUpdateRequestDto memberGradeUpdateRequestDto) {
        this.pointRate = memberGradeUpdateRequestDto.getPointRate();
        this.standardAmount = memberGradeUpdateRequestDto.getStandardAmount();
    }
}
