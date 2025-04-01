package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAccountService {

	private final MemberAccountRepository memberAccountRepository;
	private final GradeRepository gradeRepository;
	private final PasswordEncoder passwordEncoder;

	public void create(Member member) {
		member.setRole(AccountRole.MEMBER);
		member.setStatus(AccountStatus.ACTIVE);
		member.setTotalPoint(5000);
		member.setEncodePassword(passwordEncoder.encode(member.getPassword()));
		Grade grade = gradeRepository.findByDefaultGrade()
			.orElseThrow(() -> new BusinessException(ErrorMessage.GRADE_NOT_EXISTS));
		member.setGrade(grade);
		memberAccountRepository.save(member);

	}
}
