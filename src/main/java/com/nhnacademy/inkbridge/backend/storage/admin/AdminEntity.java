package com.nhnacademy.inkbridge.backend.storage.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;
import com.nhnacademy.inkbridge.backend.domain.AccountStatus;
import com.nhnacademy.inkbridge.backend.domain.admin.Admin;
import com.nhnacademy.inkbridge.backend.storage.support.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminEntity extends BaseEntity {

	@Id
	@Column(name = "admin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "account_role")
	private AccountRole accountRole;

	@Enumerated(EnumType.STRING)
	@Column(name="account_status")
	private AccountStatus accountStatus;
	@Column(name = "last_login_at")
	private DateTime lastLoginAt;

	@Builder
	public AdminEntity(String email, String password, String name, AccountRole accountRole, AccountStatus accountStatus) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.accountRole = accountRole;
		this.accountStatus = accountStatus;
	}

	public void update(Admin admin){
		this.email = admin.getEmail();
		this.password = admin.getPassword();
		this.name = admin.getName();
	}

	public void delete() {
		this.accountStatus = AccountStatus.DELETED;
	}
}
