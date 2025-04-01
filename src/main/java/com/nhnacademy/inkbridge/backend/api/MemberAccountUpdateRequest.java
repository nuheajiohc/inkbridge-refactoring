package com.nhnacademy.inkbridge.backend.api;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.nhnacademy.inkbridge.backend.domain.Member;

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

	public Member toMember(){
		return Member.builder()
			.name(name)
			.password(password)
			.phoneNumber(phoneNumber)
			.build();
	}
}
