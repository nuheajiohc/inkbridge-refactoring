package com.nhnacademy.inkbridge.backend.dto.deliverypolicy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class: DeliveryPolicyReadResponseDto.
 *
 * @author jangjaehun
 * @version 2024/03/01
 */
@Getter
@RequiredArgsConstructor
public class DeliveryPolicyReadResponseDto {

    private final Long deliveryPolicyId;
    private final Long deliveryPrice;
    private final Long freeDeliveryPrice;
}
