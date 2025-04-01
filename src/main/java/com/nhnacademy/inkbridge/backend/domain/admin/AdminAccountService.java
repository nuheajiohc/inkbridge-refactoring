package com.nhnacademy.inkbridge.backend.domain.admin;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;
import com.nhnacademy.inkbridge.backend.domain.AccountStatus;
import com.nhnacademy.inkbridge.backend.domain.BusinessException;
import com.nhnacademy.inkbridge.backend.domain.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminAccountService {

	private final AdminAccountRepository adminAccountRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(Admin admin) {
		admin.setRole(AccountRole.SUB_ADMIN);
		admin.setStatus(AccountStatus.ACTIVE);
		admin.setEncodePassword(passwordEncoder.encode(admin.getPassword()));
		adminAccountRepository.save(admin);
	}

	public void update(Integer loginId, Admin admin) {
		admin.setEncodePassword(passwordEncoder.encode(admin.getPassword()));
		adminAccountRepository.update(loginId, admin);
	}

	public void delete(Integer loginId) {
		adminAccountRepository.delete(loginId);
	}
}
