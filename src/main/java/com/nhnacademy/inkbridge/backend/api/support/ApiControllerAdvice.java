package com.nhnacademy.inkbridge.backend.api.support;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nhnacademy.inkbridge.backend.domain.BusinessException;


@RestControllerAdvice
public class ApiControllerAdvice {

	@ExceptionHandler(BusinessException.class)
	public ApiErrorResponse<String> handleBusinessException(BusinessException e) {
		return ApiErrorResponse.error(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErrorResponse<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> errors = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				FieldError::getDefaultMessage,
				(existing, next) -> existing // 필드가 중복될 경우 첫 번째 메시지를 사용
			));

		return ApiErrorResponse.error(errors);
	}
}
