package com.nhnacademy.inkbridge.backend.api.support.response;

import lombok.Getter;

@Getter
public class ApiErrorResponse<T> {

	private final ResultStatus status;
	private final T message;

	public ApiErrorResponse(ResultStatus status, T message) {
		this.status = status;
		this.message = message;
	}

	public static <T> ApiErrorResponse<T> error(T message) {
		return new ApiErrorResponse<>(ResultStatus.ERROR, message);
	}
}
