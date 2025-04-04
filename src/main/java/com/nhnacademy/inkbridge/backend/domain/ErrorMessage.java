package com.nhnacademy.inkbridge.backend.domain;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	ADDRESS_LIMIT_EXCEEDED("주소를 더이상 등록할 수 없습니다."),
	ADDRESS_NOT_EXISTS("주소가 존재하지 않습니다."),
	ADDRESS_DELETE_FORBIDDEN("주소를 삭제할 수 없습니다."),
	ADDRESS_NOT_OWNED("본인의 주소가 아닙니다."),


	ACCOUNT_NOT_EXISTS("계정이 존재하지 않습니다."),
	ACCOUNT_UPDATE_FORBIDDEN("계정 업데이트를 할 수 없습니다."),

	DATE_FORMAT_NOT_SUPPORTED("지원하지 않는 날짜 형식입니다."),

	GRADE_NOT_EXISTS("등급이 존재하지 않습니다."),

	PUBLISHER_DUPLICATED("중복된 출판사는 등록할 수 없습니다."),
	PUBLISHER_NOT_EXISTS("출판사가 존재하지 않습니다.");

	private final String text;

	ErrorMessage(String text) {
		this.text = text;
	}
}
