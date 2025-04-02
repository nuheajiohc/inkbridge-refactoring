package com.nhnacademy.inkbridge.backend.storage;

import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.BusinessException;
import com.nhnacademy.inkbridge.backend.domain.ErrorMessage;
import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.Member;
import com.nhnacademy.inkbridge.backend.domain.MemberAccountRepository;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberAccountCoreRepository implements MemberAccountRepository {

	private final MemberAccountJpaRepository memberAccountJpaRepository;
	private final GradeJpaRepository gradeJpaRepository;

	@Override
	public void save(Member member) {
		memberAccountJpaRepository.save(
			MemberEntity.builder()
				.name(member.getName())
				.password(member.getPassword())
				.birth(member.getBirth())
				.email(member.getEmail())
				.phoneNumber(member.getPhoneNumber())
				.gradeEntity(gradeJpaRepository.getReferenceById(member.getGrade().getId()))
				.accountStatus(member.getStatus())
				.totalPoint(member.getTotalPoint())
				.build()
		);
	}

	@Override
	public void update(Long loginId, EssentialAccountInfo essentialAccountInfo, ProfileInfo profileInfo) {
		MemberEntity memberEntity = memberAccountJpaRepository.findById(loginId)
			.orElseThrow(() -> new BusinessException(ErrorMessage.ACCOUNT_NOT_EXISTS));
		memberEntity.update(essentialAccountInfo, profileInfo);
	}

	@Override
	public void delete(Long loginId) {
		MemberEntity memberEntity = memberAccountJpaRepository.findById(loginId)
			.orElseThrow(() -> new BusinessException(ErrorMessage.ACCOUNT_NOT_EXISTS));
		memberEntity.delete();
	}
}
