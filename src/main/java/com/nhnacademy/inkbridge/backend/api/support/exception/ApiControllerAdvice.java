package com.nhnacademy.inkbridge.backend.api.support.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nhnacademy.inkbridge.backend.api.support.response.ApiErrorResponse;
import com.nhnacademy.inkbridge.backend.domain.BusinessException;


@RestControllerAdvice
public class ApiControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public ApiErrorResponse<String> handleBusinessException(BusinessException e) {
		return ApiErrorResponse.error(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiErrorResponse<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> errors = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				FieldError::getDefaultMessage,
				(existing, next) -> existing
			));

		return ApiErrorResponse.error(errors);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiErrorResponse<Map<String, String>> handleBindException(BindException e) {
		Map<String, String> errors = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				FieldError::getDefaultMessage,
				(existing, next) -> existing
			));

		return ApiErrorResponse.error(errors);
	}
}
