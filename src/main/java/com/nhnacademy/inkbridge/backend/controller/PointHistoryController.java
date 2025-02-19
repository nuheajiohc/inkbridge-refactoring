package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.controller.header.HeaderConstants;
import com.nhnacademy.inkbridge.backend.dto.member.PointHistoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.PointHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PointHistoryController.
 *
 * @author jeongbyeonghun
 * @version 3/22/24
 */
@RestController
@RequestMapping("/api/mypage/pointHistory")
@RequiredArgsConstructor
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;

    /**
     * 특정 사용자의 포인트 이력을 조회합니다.
     * 사용자 ID를 기반으로 해당 사용자의 모든 포인트 변동 이력을 조회하여 반환합니다.
     *
     * @param userId 조회할 사용자의 ID. 사용자를 식별하기 위해 `Authorization-Id` 헤더를 통해 전달받습니다.
     * @return 조회된 포인트 이력 정보를 담은 {@link PointHistoryReadResponseDto} 리스트와 함께 HTTP 상태 코드 200(OK)을 반환합니다.
     */
    @GetMapping
    public ResponseEntity<List<PointHistoryReadResponseDto>> getPointHistory(
        @RequestHeader(HeaderConstants.MEMBER_ID_HEADER) Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(pointHistoryService.getPointHistory(userId));
    }
}
