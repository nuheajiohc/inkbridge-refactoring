package com.nhnacademy.inkbridge.backend.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.nhnacademy.inkbridge.backend.domain.AccountStatus;
import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.Member;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;
import com.nhnacademy.inkbridge.backend.storage.support.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "birth")
	private LocalDate birth;
	@Column(name = "total_point")
	private Integer totalPoint;
	@Enumerated(EnumType.STRING)
	@Column(name = "account_status")
	private AccountStatus accountStatus;
	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id")
	private GradeEntity gradeEntity;

	@Builder
	public MemberEntity(String email, String password, String name, String phoneNumber, LocalDate birth,
		Integer totalPoint, AccountStatus accountStatus, GradeEntity gradeEntity) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.birth = birth;
		this.totalPoint = totalPoint;
		this.accountStatus = accountStatus;
		this.gradeEntity = gradeEntity;
	}

	public void update(EssentialAccountInfo essentialAccountInfo, ProfileInfo profileInfo) {
		this.password = essentialAccountInfo.getPassword();
		this.name = profileInfo.getName();
		this.phoneNumber = profileInfo.getPhoneNumber();
	}

	public void delete() {
		accountStatus = AccountStatus.DELETED;
	}
}
