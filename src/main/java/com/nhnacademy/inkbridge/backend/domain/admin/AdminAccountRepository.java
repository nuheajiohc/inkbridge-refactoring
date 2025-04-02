package com.nhnacademy.inkbridge.backend.domain.admin;

import com.nhnacademy.inkbridge.backend.domain.EssentialAccountInfo;
import com.nhnacademy.inkbridge.backend.domain.ProfileInfo;

public interface AdminAccountRepository {
	void save(Admin admin);

	void update(Integer loginId, EssentialAccountInfo essentialAccountInfo, ProfileInfo profileInfo);

	void delete(Integer loginId);

}
