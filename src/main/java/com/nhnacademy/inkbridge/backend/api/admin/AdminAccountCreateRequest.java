package com.nhnacademy.inkbridge.backend.api.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nhnacademy.inkbridge.backend.domain.admin.Admin;

import lombok.Getter;

@Getter
public class AdminAccountCreateRequest {

	private final String name;
	private final String email;
	private final String password;

	public AdminAccountCreateRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Admin toAdmin() {
		return Admin.builder()
			.name(name)
			.email(email)
			.password(password)
			.build();
	}
}
