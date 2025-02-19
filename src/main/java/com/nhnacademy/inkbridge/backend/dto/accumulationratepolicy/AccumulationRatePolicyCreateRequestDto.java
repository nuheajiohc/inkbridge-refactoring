package com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: AccumulationRatePolicyCreateRequestDto.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@Getter
@Setter
@NoArgsConstructor
public class AccumulationRatePolicyCreateRequestDto {

    @Min(value = 0, message = "적립율 비율은 음수일 수 없습니다.")
    @Max(value = 100, message = "적립율 비율은 100을 넘을 수 없습니다.")
    @NotNull(message = "적립율은 필수 입력 항목입니다.")
    private Integer accumulationRate;
}
