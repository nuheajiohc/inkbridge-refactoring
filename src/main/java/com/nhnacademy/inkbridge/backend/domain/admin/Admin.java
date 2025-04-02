package com.nhnacademy.inkbridge.backend.domain.admin;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;
import com.nhnacademy.inkbridge.backend.domain.AccountStatus;
import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Admin {

	private String name;
	private String password;
	private String email;
	private AccountRole role;
	private AccountStatus status;

	@Builder
	public Admin(String name, String password, String email, AccountRole role, AccountStatus status) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
		this.status = status;
	}

	public static Admin create(EssentialAccountInfo accountInfo, ProfileInfo profileInfo,String encodedPassword) {
		return Admin.builder()
			.name(profileInfo.getName())
			.password(encodedPassword)
			.email(accountInfo.getEmail())
			.role(AccountRole.SUB_ADMIN)
			.status(AccountStatus.ACTIVE)
			.build();
	}
}
