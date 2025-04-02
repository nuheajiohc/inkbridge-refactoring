package com.nhnacademy.inkbridge.backend.api.admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

import lombok.Getter;

@Getter
public class AdminAccountCreateRequest {

	@NotBlank
	@Length(min = 2, max = 20)
	private final String name;
	@NotBlank
	@Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요")
	private final String email;
	@NotBlank
	@Length(min = 6, max = 20)
	private final String password;

	public AdminAccountCreateRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public EssentialAccountInfo toEssentialAccountInfo() {
		return EssentialAccountInfo.builder()
			.email(email)
			.password(password)
			.build();
	}

	public ProfileInfo toProfileInfo() {
		return ProfileInfo.builder()
			.name(name)
			.build();
	}
}
