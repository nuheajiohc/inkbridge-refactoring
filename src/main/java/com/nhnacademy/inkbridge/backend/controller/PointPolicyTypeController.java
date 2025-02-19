package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.pointpolicytype.PointPolicyTypeReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointPolicyTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PointPolicyTypeRestController.
 *
 * @author jangjaehun
 * @version 2024/02/15
 */
@RestController
@RequestMapping("/api/point-policy-types")
@RequiredArgsConstructor
public class PointPolicyTypeController {

    private final PointPolicyTypeService pointPolicyTypeService;

    /**
     * 포인트 정책 유형 전체 조회 메서드 입니다.
     *
     * @return PointPolicyTypeReadResponseDto
     */
    @GetMapping
    public ResponseEntity<List<PointPolicyTypeReadResponseDto>> getPointPolicyTypes() {

        return ResponseEntity.ok(pointPolicyTypeService.getPointPolicyTypes());
    }
}
