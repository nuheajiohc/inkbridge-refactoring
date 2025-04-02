package com.nhnacademy.inkbridge.backend.domain;

public interface MemberAccountRepository {
	void save(Member member);

	void update(Long loginId, EssentialAccountInfo essentialAccountInfo, ProfileInfo profileInfo);

	void delete(Long loginId);

}
