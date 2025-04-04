package com.nhnacademy.inkbridge.backend.domain;

public enum DomainStatus {
	ACTIVE, INACTIVE;

	public static DomainStatus getStatus(String status) {
		try {
			return DomainStatus.valueOf(status);
		}catch(IllegalArgumentException e) {
			throw new BusinessException(ErrorMessage.DOMAIN_STATUS_NOT_EXISTS);
		}
	}
}
