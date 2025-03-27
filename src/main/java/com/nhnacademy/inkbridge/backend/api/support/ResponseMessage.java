package com.nhnacademy.inkbridge.backend.api.support;

import lombok.Getter;

@Getter
public enum ResponseMessage {

	ADDRESS_CREATED("배송지가 생성되었습니다."),
	ADDRESS_UPDATED("배송지가 업데이트되었습니다."),
	ADDRESS_DELETED("배송지가 삭제되었습니다.");

	private final String text;

	ResponseMessage(String text) {
		this.text = text;
	}
}
