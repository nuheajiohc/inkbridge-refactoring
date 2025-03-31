package com.nhnacademy.inkbridge.backend.domain.admin;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Admin {

	private String name;
	private String password;
	private String email;
	private AccountRole role;

	@Builder
	public Admin(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public void setRole(AccountRole role) {
		this.role = role;
	}

	public void setEncodePassword(String password) {
		this.password = password;
	}
}
