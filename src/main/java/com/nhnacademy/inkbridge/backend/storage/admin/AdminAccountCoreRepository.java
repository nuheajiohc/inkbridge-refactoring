package com.nhnacademy.inkbridge.backend.storage.admin;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.inkbridge.backend.domain.admin.Admin;
import com.nhnacademy.inkbridge.backend.domain.admin.AdminAccountRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminAccountCoreRepository implements AdminAccountRepository {

	private final AdminAccountJpaRepository adminAccountJpaRepository;

	@Override
	public void save(Admin admin) {
		adminAccountJpaRepository.save(
			AdminEntity.builder()
				.name(admin.getName())
				.password(admin.getPassword())
				.email(admin.getEmail())
				.accountRole(admin.getRole())
				.build());
	}
}
