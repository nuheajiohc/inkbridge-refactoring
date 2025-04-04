package com.nhnacademy.inkbridge.backend.domain.admin.publisher;

import com.nhnacademy.inkbridge.backend.domain.DomainStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Publisher {

	private String name;
	private DomainStatus status;

	@Builder
	public Publisher(String name, DomainStatus status) {
		this.name = name;
		this.status = status;
	}
}
