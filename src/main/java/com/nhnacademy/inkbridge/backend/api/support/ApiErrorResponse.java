package com.nhnacademy.inkbridge.backend.api.support;

import lombok.Getter;

@Getter
public class ApiErrorResponse {

	private final ResultStatus status;
	private final String message;

	public ApiErrorResponse(ResultStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public static ApiErrorResponse error(String message) {
		return new ApiErrorResponse(ResultStatus.ERROR, message);
	}
}
