package com.nhnacademy.inkbridge.backend.dto.member;

import java.time.LocalDateTime;
import lombok.Getter;

/**
 * class: PointHistoryReadResponseDto.
 *
 * @author jeongbyeonghun
 * @version 3/22/24
 */
@Getter
public class PointHistoryReadResponseDto {


    private final String reason;

    private final Long point;

    private final LocalDateTime accruedAt;

    public PointHistoryReadResponseDto(String reason, Long point, LocalDateTime accruedAt) {
        this.reason = reason;
        this.point = point;
        this.accruedAt = accruedAt;
    }
}
