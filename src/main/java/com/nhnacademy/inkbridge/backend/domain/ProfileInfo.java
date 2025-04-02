package com.nhnacademy.inkbridge.backend.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileInfo {

	private String name;
	private String birth;
	private String phoneNumber;

	@Builder
	public ProfileInfo(String name, String birth, String phoneNumber) {
		this.name = name;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
	}
}
