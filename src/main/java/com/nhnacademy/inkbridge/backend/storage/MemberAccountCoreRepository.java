package com.nhnacademy.inkbridge.backend.storage;

import org.springframework.stereotype.Repository;

import com.nhnacademy.inkbridge.backend.domain.Member;
import com.nhnacademy.inkbridge.backend.domain.MemberAccountRepository;

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
}
