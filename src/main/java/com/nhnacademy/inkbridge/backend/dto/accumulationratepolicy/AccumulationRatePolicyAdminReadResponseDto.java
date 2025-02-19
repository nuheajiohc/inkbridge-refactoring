package com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: AccumulationRatePolicyAdminReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/05
 */
@Getter
@RequiredArgsConstructor
public class AccumulationRatePolicyAdminReadResponseDto {
    private final Long accumulationRatePolicyId;
    private final Integer accumulationRate;
    private final LocalDate createdAt;
}
