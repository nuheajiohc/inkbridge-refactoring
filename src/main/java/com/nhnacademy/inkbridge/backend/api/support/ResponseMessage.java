package com.nhnacademy.inkbridge.backend.api.support;

import lombok.Getter;

@Getter
public enum ResponseMessage {

	// 주소
	ADDRESS_CREATED("배송지가 생성되었습니다."),
	ADDRESS_UPDATED("배송지가 업데이트되었습니다."),
	ADDRESS_DELETED("배송지가 삭제되었습니다."),
	ADDRESS_READ_SUCCESS("배송지 조회 성공하였습니다."),

	// 회원등급
	GRADE_READ_SUCCESS("회원등급 조회 성공하였습니다."),

	// 계정
	ACCOUNT_CREATED("계정 생성완료했습니다."),
	ACCOUNT_UPDATED("계정 수정완료되었습니다."),
	ACCOUNT_DELETED("걔정 삭제완료되었습니다."),

	PUBLISHER_CREATED("출판사 생성완료했습니다."),
	PUBLISHER_UPDATED("출판사 수정완료되었습니다."),
	PUBLISHER_DELETED("출판사 삭제완료되었습니다.");

	private final String text;

	ResponseMessage(String text) {
		this.text = text;
	}
}
