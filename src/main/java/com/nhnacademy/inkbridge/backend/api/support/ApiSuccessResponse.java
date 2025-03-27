package com.nhnacademy.inkbridge.backend.api.support;

import lombok.Getter;

@Getter
public class ApiSuccessResponse<T> {

	private ResultStatus status;
	private T data;
	private String message;

	public ApiSuccessResponse(ResultStatus status, T data, ResponseMessage message) {
		this.status = status;
		this.data = data;
		this.message = message.getText();
	}

	public static ApiSuccessResponse<Void> success(ResponseMessage message) {
		return new ApiSuccessResponse<>(ResultStatus.SUCCESS, null, message);
	}

	public static <T> ApiSuccessResponse<T> success(T data, ResponseMessage message) {
		return new ApiSuccessResponse<>(ResultStatus.SUCCESS, data, message);
	}
}
