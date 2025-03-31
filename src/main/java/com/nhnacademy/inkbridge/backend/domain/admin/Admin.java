package com.nhnacademy.inkbridge.backend.domain.admin;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Admin {

	private Integer id;
	private String name;
	private String password;
	private String email;
	private AccountRole role;

	@Builder
	public Admin(Integer id, String name, String password, String email, AccountRole role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public void setRole(AccountRole role) {
		this.role = role;
	}

	public void setEncodePassword(String password) {
		this.password = password;
	}
}
