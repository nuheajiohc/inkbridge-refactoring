package com.nhnacademy.inkbridge.backend.domain.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminAccountService {

	private final AdminAccountRepository adminAccountRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(EssentialAccountInfo essentialInfo, ProfileInfo profileInfo) {
		Admin admin = Admin.create(essentialInfo, profileInfo,passwordEncoder.encode(essentialInfo.getPassword()));
		adminAccountRepository.save(admin);
	}

	public void update(Integer loginId, EssentialAccountInfo essentialInfo, ProfileInfo profileInfo) {
		String encodedPassword =  passwordEncoder.encode(essentialInfo.getPassword());
		adminAccountRepository.update(loginId, new EssentialAccountInfo(essentialInfo.getEmail(), encodedPassword), profileInfo);
	}

	public void delete(Integer loginId) {
		adminAccountRepository.delete(loginId);
	}
}
