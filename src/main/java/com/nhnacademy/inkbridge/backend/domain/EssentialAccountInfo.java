package com.nhnacademy.inkbridge.backend.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EssentialAccountInfo {

	private String password;
	private String email;

	@Builder
	public EssentialAccountInfo(String email,String password) {
		this.email = email;
		this.password = password;
	}
}
