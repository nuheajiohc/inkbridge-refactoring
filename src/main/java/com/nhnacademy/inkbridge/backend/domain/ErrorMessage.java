package com.nhnacademy.inkbridge.backend.domain;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	ADDRESS_LIMIT_EXCEEDED("주소를 더이상 등록할 수 없습니다."),
	ADDRESS_NOT_EXISTS("주소가 존재하지 않습니다."),
	ADDRESS_DELETE_FORBIDDEN("주소를 삭제할 수 없습니다."),

	ACCOUNT_NOT_EXISTS("계정이 존재하지 않습니다."),
	ACCOUNT_UPDATE_FORBIDDEN("계정 업데이트를 할 수 없습니다.");

	private final String text;

	ErrorMessage(String text) {
		this.text = text;
	}
}
