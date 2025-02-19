package com.nhnacademy.inkbridge.backend.dto.pointpolicy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: PointPolicyReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@RequiredArgsConstructor
public class PointPolicyReadResponseDto {

    private final Long pointPolicyId;
    private final String policyType;
    private final Long accumulatePoint;
}
