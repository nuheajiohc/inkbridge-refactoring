package com.nhnacademy.inkbridge.backend.domain;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	ADDRESS_LIMIT_EXCEEDED("주소를 더이상 등록할 수 없습니다.");

	private final String text;

	ErrorMessage(String text) {
		this.text = text;
	}
}
