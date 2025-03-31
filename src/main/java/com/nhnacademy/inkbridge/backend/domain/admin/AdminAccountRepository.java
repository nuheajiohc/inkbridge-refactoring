package com.nhnacademy.inkbridge.backend.domain.admin;

public interface AdminAccountRepository {
	void save(Admin admin);

	void update(Admin admin);

	void delete(Integer loginId);
}
