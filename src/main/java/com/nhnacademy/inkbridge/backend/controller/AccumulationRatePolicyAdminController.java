package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.accumulationratepolicy.AccumulationRatePolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.AccumulationRatePolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.AccumulationRatePolicyService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: AccumulationRatePolicyAdminController.
 *
 * @author jangjaehun
 * @version 2024/03/05
 */
@RestController
@RequestMapping("/api/admin/accumulation-rate-policies")
@RequiredArgsConstructor
public class AccumulationRatePolicyAdminController {

    private final AccumulationRatePolicyService accumulationRatePolicyService;

    /**
     * 기본 적립율 정책 전체 내역을 조회하는 메소드입니다.
     *
     * @return AccumulationRatePolicyReadResponseDto
     */
    @GetMapping
    public ResponseEntity<List<AccumulationRatePolicyAdminReadResponseDto>> getAccumulationRatePolicies() {

        return ResponseEntity.ok(accumulationRatePolicyService.getAccumulationRatePolicies());
    }

    /**
     * 기본 적립율 정책을 생성하는 메소드입니다.
     *
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> createAccumulationRatePolicy(
        @RequestBody @Valid AccumulationRatePolicyCreateRequestDto requestDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                AccumulationRatePolicyMessageEnum.ACCUMULATION_RATE_POLICY_VALID_FAIL.getMessage());
        }

        accumulationRatePolicyService.createAccumulationRatePolicy(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
