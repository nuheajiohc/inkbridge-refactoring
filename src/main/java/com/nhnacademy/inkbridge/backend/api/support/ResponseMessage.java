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

	// 관리자
	ADMIN_ACCOUNT_CREATED("관리자 계정 생성완료했습니다."),
	ADMIN_ACCOUNT_UPDATED("관리자 계정 수정완료되었습니다.");

	private final String text;

	ResponseMessage(String text) {
		this.text = text;
	}
}
