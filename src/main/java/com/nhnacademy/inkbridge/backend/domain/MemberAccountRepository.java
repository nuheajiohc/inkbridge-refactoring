package com.nhnacademy.inkbridge.backend.domain;

public interface MemberAccountRepository {
	void save(Member member);

	void update(Integer loginId, Member member);

	void delete(Integer loginId);
}
