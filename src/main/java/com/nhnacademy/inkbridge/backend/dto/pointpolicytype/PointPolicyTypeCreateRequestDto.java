package com.nhnacademy.inkbridge.backend.dto.pointpolicytype;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * class: PointPolicyTypeCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@Setter
@NoArgsConstructor
public class PointPolicyTypeCreateRequestDto {
    @Length(max = 20, message = "포인트 정책 유형의 허용 글자수는 0~20자 사이입니다.")
    @NotBlank(message = "포인트 정책 유형은 필수 입력 항목입니다.")
    private String policyType;

    @NotNull(message = "적립 포인트는 필수 입력 항목입니다.")
    @Min(value = 0, message = "적립 포인트는 음수 일수 없습니다.")
    private Long accumulatePoint;
}
