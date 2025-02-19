package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.order.WrappingResponseDto;
import com.nhnacademy.inkbridge.backend.service.WrappingService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: WrappingController.
 *
 * @author JBum
 * @version 2024/02/28
 */

@RestController
@RequestMapping("/api/wrappings")
public class WrappingController {

    private final WrappingService wrappingService;

    /**
     * wrappingController 생성자.
     *
     * @param wrappingService 주입해 줄 wrappingService
     */

    public WrappingController(WrappingService wrappingService) {
        this.wrappingService = wrappingService;
    }

    /**
     * 모든 wrapping을 제공해주는 메소드.
     *
     * @param isActive 활성여부 default ture
     * @return 모든 wrapping
     */
    @GetMapping
    public ResponseEntity<List<WrappingResponseDto>> getWrappings(
        @RequestParam(value = "is_active", defaultValue = "true") boolean isActive) {
        return ResponseEntity.ok(wrappingService.getWrappingList(isActive));
    }

    /**
     * 특정 wrapping을 제공해주는 메소드
     *
     * @param wrappingId 제공을원하는 wrapping의 id
     * @return 선택된 wrapping
     */
    @GetMapping("/{wrappingId}")
    public ResponseEntity<WrappingResponseDto> getWrapping(
        @PathVariable("wrappingId") Long wrappingId) {
        return ResponseEntity.ok(wrappingService.getWrapping(wrappingId));
    }
}
