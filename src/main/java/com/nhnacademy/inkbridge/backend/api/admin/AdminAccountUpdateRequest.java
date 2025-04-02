package com.nhnacademy.inkbridge.backend.api.admin;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

public class AdminAccountUpdateRequest {

	@NotBlank
	@Length(min = 2, max = 20)
	private final String name;
	@NotBlank
	@Length(min = 6, max = 20)
	private final String password;

	public AdminAccountUpdateRequest(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public EssentialAccountInfo toEssentialAccountInfo() {
		return EssentialAccountInfo.builder()
			.password(password)
			.build();
	}

	public ProfileInfo toProfileInfo() {
		return ProfileInfo.builder()
			.name(name)
			.build();
	}
}
