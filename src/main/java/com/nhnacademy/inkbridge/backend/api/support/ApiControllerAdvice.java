package com.nhnacademy.inkbridge.backend.api.support;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nhnacademy.inkbridge.backend.domain.BusinessException;


@RestControllerAdvice
public class ApiControllerAdvice {

	@ExceptionHandler(BusinessException.class)
	public ApiErrorResponse handleBusinessException(BusinessException e) {
		return ApiErrorResponse.error(e.getMessage());
	};
}
