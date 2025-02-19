package com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: AccumulationRatePolicyReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@Getter
@RequiredArgsConstructor
public class AccumulationRatePolicyReadResponseDto {
    private final Long accumulationRatePolicyId;
    private final Integer accumulationRate;
}
