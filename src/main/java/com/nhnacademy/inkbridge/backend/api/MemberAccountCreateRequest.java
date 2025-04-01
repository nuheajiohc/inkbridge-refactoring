package com.nhnacademy.inkbridge.backend.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.nhnacademy.inkbridge.backend.domain.Member;

import lombok.Getter;

@Getter
public class MemberAccountCreateRequest {

	@NotBlank
	@Length(min = 2, max = 20)
	private final String name;
	@NotBlank
	@Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$", message="이메일 주소 양식을 확인해주세요")
	private final String email;
	@NotBlank
	@Length(min = 6, max = 20)
	private final String password;

	@NotBlank
	private final String phoneNumber;

	@NotNull
	@Length(min=8, max=10)
	private final String birth;

	public MemberAccountCreateRequest(String name, String email, String password, String phoneNumber,
		String birth) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.birth = birth;
	}

	public Member toMember(){
		return Member.builder()
			.name(name)
			.email(email)
			.password(password)
			.phoneNumber(phoneNumber)
			.birth(birth)
			.build();
	}
}
