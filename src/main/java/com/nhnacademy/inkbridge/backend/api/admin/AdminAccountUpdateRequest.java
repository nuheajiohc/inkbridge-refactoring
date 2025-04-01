package com.nhnacademy.inkbridge.backend.api.admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.nhnacademy.inkbridge.backend.domain.admin.Admin;

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

	public Admin toAdmin(){
		return Admin.builder()
			.name(name)
			.password(password)
			.build();
	}
}
