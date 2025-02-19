package com.nhnacademy.inkbridge.backend.dto.pointpolicy;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: PointPolicyAdminReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/05
 */
@Getter
@RequiredArgsConstructor
public class PointPolicyAdminReadResponseDto {

    private final Long pointPolicyId;
    private final String policyType;
    private final Long accumulatePoint;
    private final LocalDate createdAt;
}
