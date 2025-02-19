package com.nhnacademy.inkbridge.backend.dto.order;

import lombok.Getter;

/**
 * class: WrappingResponseDto.
 *
 * @author JBum
 * @version 2024/02/28
 */
@Getter
public class WrappingResponseDto {

    private Long wrappingId;

    private String wrappingName;

    private Long price;

    private Boolean isActive;

    public WrappingResponseDto(Long wrappingId, String wrappingName, Long price, Boolean isActive) {
        this.wrappingId = wrappingId;
        this.wrappingName = wrappingName;
        this.price = price;
        this.isActive = isActive;
    }
}
