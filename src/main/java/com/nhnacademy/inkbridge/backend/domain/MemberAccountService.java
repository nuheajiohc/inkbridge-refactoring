package com.nhnacademy.inkbridge.backend.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAccountService {

	private final MemberAccountCommandHandler accountCommandHandler;
	private final GradeReader gradeReader;

	public void signup(EssentialAccountInfo essentialInfo, ProfileInfo profileInfo) {
		Grade grade = gradeReader.getDefaultGrade()
			.orElseThrow(() -> new BusinessException(ErrorMessage.GRADE_NOT_EXISTS));
		accountCommandHandler.create(essentialInfo, profileInfo, grade);
	}

	public void update(Long loginId, EssentialAccountInfo essentialInfo, ProfileInfo profileInfo) {
		accountCommandHandler.update(loginId, essentialInfo, profileInfo);
	}

	public void delete(Long loginId) {
		accountCommandHandler.delete(loginId);
	}
}
