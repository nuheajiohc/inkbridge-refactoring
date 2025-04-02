package com.nhnacademy.inkbridge.backend.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Member {

	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	private Birth birth;
	@Setter
	private Integer totalPoint;
	@Setter
	private AccountRole role;
	@Setter
	private AccountStatus status;
	@Setter
	private Grade grade;

	@Builder
	public Member(String name, String email, String password, String phoneNumber, String birth, Integer totalPoint,
		AccountRole role, AccountStatus status, Grade grade) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.birth = birth == null ? null : Birth.from(birth);
		this.totalPoint = totalPoint;
		this.role = role;
		this.status = status;
		this.grade = grade;
	}

	public static Member create(EssentialAccountInfo essentialInfo, ProfileInfo profileInfo, String encodedPassword, Grade grade){
		return Member.builder()
			.name(profileInfo.getName())
			.email(essentialInfo.getEmail())
			.password(encodedPassword)
			.phoneNumber(profileInfo.getPhoneNumber())
			.birth(profileInfo.getBirth())
			.totalPoint(5000)
			.role(AccountRole.MEMBER)
			.status(AccountStatus.ACTIVE)
			.grade(grade)
			.build();
	}

	public LocalDate getBirth() {
		return birth.getValue();
	}
}
