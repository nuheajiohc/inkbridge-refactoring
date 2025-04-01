package com.nhnacademy.inkbridge.backend.domain.admin;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;
import com.nhnacademy.inkbridge.backend.domain.AccountStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Admin {

	private String name;
	private String password;
	private String email;
	@Setter
	private AccountRole role;
	@Setter
	private AccountStatus status;

	@Builder
	public Admin(String name, String password, String email, AccountRole role, AccountStatus status) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
		this.status = status;
	}

	public void setEncodePassword(String password) {
		this.password = password;
	}

}
