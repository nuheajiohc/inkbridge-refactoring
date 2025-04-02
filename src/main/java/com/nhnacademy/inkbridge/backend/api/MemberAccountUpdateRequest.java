package com.nhnacademy.inkbridge.backend.api;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

public class MemberAccountUpdateRequest {

	@NotBlank
	@Length(min = 2, max = 20)
	private String name;
	@NotBlank
	@Length(min = 6, max = 20)
	private String password;
	@NotBlank
	private String phoneNumber;

	public MemberAccountUpdateRequest(String name, String password, String phoneNumber) {
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public EssentialAccountInfo toEssentialAccountInfo() {
		return EssentialAccountInfo.builder()
			.password(password)
			.build();
	}

	public ProfileInfo toProfileInfo() {
		return ProfileInfo.builder()
			.name(name)
			.phoneNumber(phoneNumber)
			.build();
	}
}
