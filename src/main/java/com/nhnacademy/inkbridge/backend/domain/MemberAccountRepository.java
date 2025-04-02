package com.nhnacademy.inkbridge.backend.domain;

public interface MemberAccountRepository {
	void save(Member member);

	void update(Integer loginId, EssentialAccountInfo essentialAccountInfo, ProfileInfo profileInfo);

	void delete(Integer loginId);

}
