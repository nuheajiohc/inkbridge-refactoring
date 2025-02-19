package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AccumulationRatePolicyController.
 *
 * @author jangjaehun
 * @version 2024/02/21
 */
@RestController
@RequestMapping("/api/accumulation-rate-policies")
@RequiredArgsConstructor
public class AccumulationRatePolicyController {

    private final AccumulationRatePolicyService accumulationRatePolicyService;

    /**
     * 적립율 정책 id로 적립율 정책을 조회하는 메소드입니다.
     *
     * @param accumulationRatePolicyId Integer
     * @return AccumulationRatePolicyReadResponseDto
     */
    @GetMapping("/{accumulationRatePolicyId}")
    public ResponseEntity<AccumulationRatePolicyReadResponseDto> getAccumulationRatePolicyById(
        @PathVariable Long accumulationRatePolicyId) {

        return ResponseEntity.ok(
            accumulationRatePolicyService.getAccumulationRatePolicy(accumulationRatePolicyId));
    }

    /**
     * 현재 적용되는 적립율 정책을 조회하는 메소드입니다.
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    @GetMapping("/current")
    public ResponseEntity<AccumulationRatePolicyReadResponseDto> getCurrentAccumulationRatePolicy() {
        return ResponseEntity.ok(accumulationRatePolicyService.getCurrentAccumulationRatePolicy());
    }


}
