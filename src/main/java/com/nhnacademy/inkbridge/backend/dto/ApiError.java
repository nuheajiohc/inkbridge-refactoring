package com.nhnacademy.inkbridge.backend.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * class: ApiError.
 *
 * @author minm063
 * @version 2024/02/15
 */
@Getter
public class ApiError {
    private final String message;

    @Builder
    public ApiError(String message) {
        this.message = message;
    }
}
