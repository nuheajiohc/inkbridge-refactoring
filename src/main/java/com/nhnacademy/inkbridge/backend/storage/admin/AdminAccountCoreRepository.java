package com.nhnacademy.inkbridge.backend.storage.admin;

import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.BusinessException;
import com.nhnacademy.inkbridge.backend.domain.ErrorMessage;
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

	@Override
	public void update(Admin admin) {
		AdminEntity adminEntity = adminAccountJpaRepository.findById(admin.getId())
			.orElseThrow(() -> new BusinessException(ErrorMessage.ACCOUNT_NOT_EXISTS));
		adminEntity.update(admin);
	}
}
