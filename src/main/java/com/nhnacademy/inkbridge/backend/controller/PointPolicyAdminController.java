package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.pointpolicy.PointPolicyCreateRequestDto;
import com.nhnacademy.inkbridge.backend.enums.PointPolicyMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.PointPolicyService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PointPolicyAdminController.
 *
 * @author jangjaehun
 * @version 2024/03/05
 */
@RestController
@RequestMapping("/api/admin/point-policies")
@RequiredArgsConstructor
public class PointPolicyAdminController {

    private final PointPolicyService pointPolicyService;

    /**
     * 포인트 정책 전체 조회 메서드 입니다.
     *
     * @return PointPolicyAdminReadResponseDto
     */
    @GetMapping
    public ResponseEntity<List<PointPolicyAdminReadResponseDto>> getPointPolicies() {
        return ResponseEntity.ok(pointPolicyService.getPointPolicies());
    }

    /**
     * 포인트 정책 유형 id로 포인트 정책 내역을 조회하는 메소드 입니다.
     *
     * @param pointPolicyTypeId Integer
     * @return List - PointPolicyAdminReadResponseDto
     */
    @GetMapping("/{pointPolicyTypeId}")
    public ResponseEntity<List<PointPolicyAdminReadResponseDto>> getPointPoliciesByTypeId(
        @PathVariable Integer pointPolicyTypeId) {

        return ResponseEntity.ok(pointPolicyService.getPointPoliciesByTypeId(pointPolicyTypeId));
    }

    /**
     * 등록된 모든 유형의 현재 적용중인 정책들을 조회하는 메소드입니다.
     *
     * @return List - PointPolicyAdminReadResponseDto
     */
    @GetMapping("/current")
    public ResponseEntity<List<PointPolicyAdminReadResponseDto>> getCurrentPointPolicies() {
        return ResponseEntity.ok(pointPolicyService.getCurrentPointPolicies());
    }

    /**
     * 포인트 정책 생성 메서드 입니다.
     *
     * @param pointPolicyCreateRequestDto PointPolicyCreateRequestDto
     * @param bindingResult               BindingResult
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> createPointPolicy(
        @RequestBody @Valid PointPolicyCreateRequestDto pointPolicyCreateRequestDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                PointPolicyMessageEnum.POINT_POLICY_VALID_FAIL.getMessage());
        }

        pointPolicyService.createPointPolicy(pointPolicyCreateRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
