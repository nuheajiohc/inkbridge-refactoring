package com.nhnacademy.inkbridge.backend.domain;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	ADDRESS_LIMIT_EXCEEDED("주소를 더이상 등록할 수 없습니다."),
	ADDRESS_NOT_EXISTS("주소가 존재하지 않습니다.");

	private final String text;

	ErrorMessage(String text) {
		this.text = text;
	}
}
