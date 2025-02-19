package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: DeliveryPolicyController.
 *
 * @author jangjaehun
 * @version 2024/02/19
 */
@RestController
@RequestMapping("/api/delivery-policies")
@RequiredArgsConstructor
public class DeliveryPolicyController {

    private final DeliveryPolicyService deliveryPolicyService;

    /**
     * 현재 적용 배송비 정책을 조회하는 메소드입니다.
     *
     * @return DeliveryPolicyReadResponseDto
     */
    @GetMapping("/current")
    public ResponseEntity<DeliveryPolicyReadResponseDto> getCurrentDeliveryPolicy() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(deliveryPolicyService.getCurrentDeliveryPolicy());
    }

}
