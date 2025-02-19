package com.nhnacademy.inkbridge.backend.dto.pointpolicytype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: PointPolicyTypeReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@Getter
@RequiredArgsConstructor
public class PointPolicyTypeReadResponseDto {

    private final Integer pointPolicyTypeId;
    private final String policyType;

}
