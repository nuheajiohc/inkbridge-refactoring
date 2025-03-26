package com.nhnacademy.inkbridge.backend.domain;

public class BusinessException extends RuntimeException {

	public BusinessException(ErrorMessage message) {
		super(message.getText());
	}
}
