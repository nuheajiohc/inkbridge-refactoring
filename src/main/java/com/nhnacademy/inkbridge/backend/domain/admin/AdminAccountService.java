package com.nhnacademy.inkbridge.backend.domain.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.inkbridge.backend.domain.AccountRole;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminAccountService {

	private final AdminAccountRepository adminAccountRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(Admin admin) {
		admin.setRole(AccountRole.SUB_ADMIN);
		admin.setEncodePassword(passwordEncoder.encode(admin.getPassword()));
		adminAccountRepository.save(admin);
	}
}
