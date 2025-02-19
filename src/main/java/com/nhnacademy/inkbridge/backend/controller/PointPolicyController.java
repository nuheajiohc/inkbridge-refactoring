package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PointPolicyController.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@RestController
@RequestMapping("/api/point-policies")
@RequiredArgsConstructor
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;


    /**
     * 포인트 정책 id에 맞는 유형의 현재 적용중인 정책을 조회하는 메소드입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return PointPolicyReadResponseDto
     */
    @GetMapping("/current/{pointPolicyTypeId}")
    public ResponseEntity<PointPolicyReadResponseDto> getCurrentPointPolicy(
        @PathVariable Integer pointPolicyTypeId) {
        return ResponseEntity.ok(pointPolicyService.getCurrentPointPolicy(pointPolicyTypeId));
    }

}
