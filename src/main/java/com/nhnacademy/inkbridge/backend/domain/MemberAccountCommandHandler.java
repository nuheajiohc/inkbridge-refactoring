package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAccountCommandHandler {

	private final MemberAccountRepository memberAccountRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(EssentialAccountInfo essentialInfo, ProfileInfo profileInfo, Grade grade) {
		String encodedPassword = passwordEncoder.encode(essentialInfo.getPassword());
		Member member =Member.create(essentialInfo, profileInfo, encodedPassword, grade);
		memberAccountRepository.save(member);
	}

	public void update(Long loginId, EssentialAccountInfo essentialInfo, ProfileInfo profileInfo) {
		String encodedPassword = passwordEncoder.encode(essentialInfo.getPassword());
		memberAccountRepository.update(loginId, new EssentialAccountInfo(essentialInfo.getEmail(), encodedPassword), profileInfo);
	}

	public void delete(Long loginId) {
		memberAccountRepository.delete(loginId);
	}
}
